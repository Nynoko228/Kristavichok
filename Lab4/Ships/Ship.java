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
    private static final Map<String, Boolean> map = new HashMap<>();


    public static void main(String[] args) throws InterruptedException {
        map.put("Бананы", false);
        map.put("Хлеб", false);
        map.put("Одежда", false);
        for (int i = 0; i < 10; i++) {
            int cargoType = ThreadLocalRandom.current().nextInt(3);
            int cargoAmount = ThreadLocalRandom.current().nextInt(1, 6);
            new Thread(new Freight(i, new ArrayList<>(map.keySet()).get(cargoType), cargoAmount)).start();
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
        }
    }

    static class Freight implements Runnable {
        private final int vesselId;
        private final String cargoType;
        private final int cargoQuantity;
        private int dockIndex; // Declare dockIndex here

        Freight(int vesselId, String cargoType, int cargoQuantity) {
            this.vesselId = vesselId;
            this.cargoType = cargoType;
            this.cargoQuantity = cargoQuantity;
        }

        @Override
        public void run() {
            try {
                synchronized (dockAvailability) {
                    if (occupiedDocks == DOCK_SIZE) {
                        dockAvailability.wait();
                    }
                    for (int j = 0; j < DOCK_SIZE; j++) {
                        if (!dockAvailability[j]) {
                            System.out.printf("Корабль №%d прибыл в бухту.%n", vesselId);
                            dockAvailability[j] = true;
                            dockIndex = j;
                            occupiedDocks++;
                            System.out.printf(YELLOW + "Корабль №%d занял причал %d. Тип груза: %s. Количество: %d%n" + RESET, vesselId, dockIndex + 1, cargoType, cargoQuantity);
                            break;
                        }
                    }
                }

                unloadCargo();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void unloadCargo() throws InterruptedException {
            unload(cargoType);
        }

        private void unload(String cargoName) throws InterruptedException {
            synchronized (cargoName.intern()) {
                synchronized (map) {
                    map.put(cargoName, true);
                }
                synchronized (dockAvailability) {
                    occupiedDocks--;
                    dockAvailability[dockIndex] = false;
                    dockAvailability.notify();
                }
                for (int k = 0; k < cargoQuantity; k++) {
                    System.out.printf(RED + "С коробля №%d отгружается груз: %s %n" + RESET, vesselId, cargoName);
                    Thread.sleep(2000);
                }
                System.out.printf(GREEN + "%s разгружены, корабль №%d покидает порт.%n" + RESET, cargoName, vesselId);
                synchronized (map) {
                    map.put(cargoName, false);
                }
            }
        }
    }
}
