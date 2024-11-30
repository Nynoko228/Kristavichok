package Ships;

import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
class ShipN {
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final int DOCK_SIZE = 5;
    private static int occupiedDocks = 0;
    private static final boolean[] dockAvailability = new boolean[DOCK_SIZE];
    private static int dockIndex;
    private static final List<Ship> ships = new ArrayList<>();
    private static int totalShips; // Общее количество кораблей
    private static int processedShips = 0; // Общее количество кораблей

    private static final Random rnd = new Random();

    public static void main(String[] args) throws InterruptedException {
        totalShips = rnd.nextInt(10) + 5; // Генерируем случайное количество кораблей (от 5 до 15)
        System.out.printf("Всего будет %d кораблей %n", totalShips);
        ShipGenerator shipGenerator = new ShipGenerator();
        Thread generator = new Thread(shipGenerator);
        Thread breadPier = new Thread(new Dock(ships, "Хлеб"));
        Thread clothesPier = new Thread(new Dock(ships, "Одежда"));
        Thread bananasPier = new Thread(new Dock(ships, "Бананы"));
        breadPier.start();
        clothesPier.start();
        bananasPier.start();
        generator.start();

        generator.join();
        breadPier.join();
        clothesPier.join();
        bananasPier.join();

        System.out.println(GREEN + "Все корабли успешно разгружены. Программа завершена." + RESET);
    }


    public static class ShipGenerator implements Runnable {
        private static final String[] CARGO_TYPES = {"Хлеб", "Одежда", "Бананы"};
        private static int nextId = 1;

        @Override
        public void run() {
            try {
                while (nextId <= totalShips) {
                    synchronized (dockAvailability) {
                        // Проверяем, есть ли свободные места
                        while (occupiedDocks == DOCK_SIZE) {
                            dockAvailability.wait(); // Ожидаем освобождения мест
                        }

                        // Генерация нового корабля
                        int vesselId = nextId++;
                        String cargoType = CARGO_TYPES[rnd.nextInt(CARGO_TYPES.length)];
                        int cargoQuantity = rnd.nextInt(5) + 1;
                        Ship ship = new Ship(vesselId, cargoType, cargoQuantity);

                        // Занимаем первое свободное место
                        occupyDock(ship);
                    }
                    Thread.sleep(ThreadLocalRandom.current().nextInt(200, 600 + 1));
                }
                System.out.println(YELLOW + "Генерация кораблей завершена." + RESET);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void occupyDock(Ship ship) {
            for (int i = 0; i < DOCK_SIZE; i++) {
                if (!dockAvailability[i]) {
                    dockAvailability[i] = true;
                    dockIndex = i;
                    System.out.printf("Корабль №%d прибыл в бухту. Тип груза: %s. Количество: %d %n", ship.getVesselId(), ship.getCargoType(), ship.getCargoQuantity());
                    occupiedDocks++;
                    synchronized (ships) {
                        ships.add(ship); // Добавляем корабль в список
                        ships.notifyAll(); // Уведомляем причалы о новом корабле
                    }
                    break;
                }
            }
        }
    }

    static class Ship {
        private final int vesselId;
        private final String cargoType;
        private final int cargoQuantity;

        Ship(int vesselId, String cargoType, int cargoQuantity) {
            this.vesselId = vesselId;
            this.cargoType = cargoType;
            this.cargoQuantity = cargoQuantity;
        }

        public int getVesselId() {
            return vesselId;
        }

        public String getCargoType() {
            return cargoType;
        }

        public int getCargoQuantity() {
            return cargoQuantity;
        }
    }

    static class Dock implements Runnable {
        private final List<Ship> ships;
        private final String cargoType;

        Dock(List<Ship> ships, String cargoType) {
            this.ships = ships;
            this.cargoType = cargoType;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Ship ship = null;
                    synchronized (ships) {
                        while (ship == null) {
                            // Ищем корабль с нужным типом груза
                            for (Ship s : ships) {
                                if (s.getCargoType().equals(cargoType)) {
                                    ship = s;
                                    ships.remove(s); // Убираем корабль из списка
                                    break;
                                }
                            }
                            if (ship == null) {
                                if (processedShips == totalShips){
                                    return;
                                }
                                ships.wait(); // Ждём новый корабль
                            }
                        }
                    }

                    // Освобождаем место в доке перед разгрузкой
                    synchronized (dockAvailability) {
                        occupiedDocks--;
                        dockAvailability[dockIndex] = false;
                        dockAvailability.notifyAll(); // Уведомляем, что место свободно
                        System.out.printf(YELLOW + "Корабль №%d занял причал для разгрузки товара типа %s.%n" + RESET,
                                ship.getVesselId(), cargoType);
                    }

                    // Начинаем разгрузку корабля
                    for (int k = 0; k < ship.getCargoQuantity(); k++) {
                        System.out.printf(RED + "С корабля №%d отгружается груз: %s. Осталось %d единиц груза.%n" + RESET,
                                ship.getVesselId(), cargoType, ship.getCargoQuantity() - k - 1);
                        Thread.sleep(1000); // Разгружаем одну единицу груза
                    }

                    System.out.printf(GREEN + "Корабль №%d полностью разгружен и покидает порт.%n" + RESET, ship.getVesselId());

                    // Увеличиваем счётчик обработанных кораблей
                    synchronized (ships) {
                        processedShips++;
                        if (processedShips == totalShips) {
                            ships.notifyAll(); // Уведомляем другие потоки, что все корабли обработаны
                            return;
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.printf("Dock for %s interrupted.%n", cargoType);
                Thread.currentThread().interrupt();
            }
        }
    }
}
