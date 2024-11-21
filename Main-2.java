package org.example;

import java.util.concurrent.ThreadLocalRandom;

class Ship {
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final int DOCK_SIZE = 5;
    private static int occupiedDocks = 0;
    private static final boolean[] dockAvailability = new boolean[DOCK_SIZE];
    private static final boolean[] bananaUnload = new boolean[1];
    private static final boolean[] breadUnload = new boolean[1];
    private static final boolean[] clothingUnload = new boolean[1];

    public static void main(String[] args) throws InterruptedException {
        bananaUnload[0] = false;
        breadUnload[0] = false;
        clothingUnload[0] = false;

        for (int i = 0; i < 10; i++) {
            int cargoType = ThreadLocalRandom.current().nextInt(1, 4);
            int cargoAmount = ThreadLocalRandom.current().nextInt(1, 6);

            switch (cargoType) {
                case 1 -> new Thread(new Freight(i, "Бананы", cargoAmount)).start();
                case 2 -> new Thread(new Freight(i, "Хлеб", cargoAmount)).start();
                case 3 -> new Thread(new Freight(i, "Одежда", cargoAmount)).start();
            }
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
        }
    }

    static class Freight implements Runnable {
        private final int vesselId;
        private final String cargoType;
        private final int cargoQuantity;
        private boolean isCompleted;
        private int dockIndex; // Declare dockIndex here

        Freight(int vesselId, String cargoType, int cargoQuantity) {
            this.vesselId = vesselId;
            this.cargoType = cargoType;
            this.cargoQuantity = cargoQuantity;
            this.isCompleted = false;
        }

        private void markAsCompleted() {
            this.isCompleted = true;
        }

        @Override
        public void run() {
            System.out.printf("Корабль #%d прибыл в порт.%n", vesselId);
            try {
                synchronized (dockAvailability) {
                    while (occupiedDocks == DOCK_SIZE) {
                        dockAvailability.wait();
                    }
                    for (int j = 0; j < DOCK_SIZE; j++) {
                        if (!dockAvailability[j]) {
                            dockAvailability[j] = true;
                            dockIndex = j; // Assign dockIndex here
                            occupiedDocks++;
                            System.out.printf(YELLOW + "Корабль #%d занял причал %d. Тип груза: %s. Количество: %d%n" + RESET, vesselId, dockIndex + 1, cargoType, cargoQuantity);
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
            switch (cargoType) {
                case "Бананы" -> unload(bananaUnload, "Бананы");
                case "Хлеб" -> unload(breadUnload, "Хлеб");
                case "Одежда" -> unload(clothingUnload, "Одежда");
            }
        }

        private void unload(boolean[] port, String cargoName) throws InterruptedException {
            synchronized (port) {
                port[0] = true;
                synchronized (dockAvailability) {
                    occupiedDocks--;
                    dockAvailability[dockIndex] = false;
                    dockAvailability.notify();
                }
                for (int k = 0; k < cargoQuantity; k++) {
                    System.out.printf(RED + "%s разгружается кораблем #%d%n" + RESET, cargoName, vesselId);
                    Thread.sleep(3000);
                }
                System.out.printf(GREEN + "%s разгружены, корабль #%d покидает порт.%n" + RESET, cargoName, vesselId);
                port[0] = false;
                markAsCompleted();
            }
        }
    }
}
