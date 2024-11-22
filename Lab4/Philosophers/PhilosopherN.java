import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher implements Runnable {
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String RED_COLOR = "\u001B[31m";
    private static final String YELLOW_COLOR = "\u001B[33m";
    private static final String GREEN_COLOR = "\u001B[32m";

    private final int philosopherNumber;
    private final Lock leftFork;
    private final Lock rightFork;
    private final Random randomGenerator;
    private final int minThoughtTime;
    private final int maxThoughtTime;
    private final int maxMealTime;
    private int mealsLeft;

    public Philosopher(int number, Lock leftFork, Lock rightFork, int minThought, int maxThought, int maxMeal, int totalMeals) {
        this.philosopherNumber = number;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.randomGenerator = new Random();
        this.minThoughtTime = minThought;
        this.maxThoughtTime = maxThought;
        this.maxMealTime = maxMeal;
        this.mealsLeft = totalMeals;
    }

    private void thinking() throws InterruptedException {
        System.out.printf(YELLOW_COLOR + "Философ " + philosopherNumber + " размышляет\n" + RESET_COLOR);
        TimeUnit.MILLISECONDS.sleep(randomGenerator.nextInt(maxThoughtTime - minThoughtTime + 1) + minThoughtTime);
    }

    private void eating() throws InterruptedException {
        System.out.printf(GREEN_COLOR + "Философ " + philosopherNumber + " ест\n" + RESET_COLOR);
        TimeUnit.MILLISECONDS.sleep(randomGenerator.nextInt(maxMealTime));
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (mealsLeft == 0) {
                    // Если философ слишком долго ждал, то он встаёт и уходит
                    System.out.printf(RED_COLOR + "Философ " + philosopherNumber + " покидает стол\n" + RESET_COLOR);
                    Thread.currentThread().interrupt();
                    break;
                } else {
                    thinking(); // Философ думает
                    mealsLeft--;
                    // Философ подумал и решил попробовать взять левую вилку
                    if (leftFork.tryLock()) {
                        try {
                            // Философ решил попробовать взять ещё и правую вилку
                            if (rightFork.tryLock()) {
                                try {
                                    // Философ поел и готов снова думать
                                    eating();
                                    mealsLeft = 2;
                                } finally {
                                    // Философ кладёт правую вилку
                                    rightFork.unlock();
                                }
                            }
                        } finally {
                            // Философ кладёт левую вилку
                            leftFork.unlock();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class DiningTable {
    private static final int NUMBER_OF_PHILOSOPHERS = 5;

    public static void main(String[] args) {
        Lock[] forks = new Lock[NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            new Thread(new Philosopher(i, forks[i], forks[(i + 1) % NUMBER_OF_PHILOSOPHERS], 1000, 3000, 500, 5)).start();
        }
    }
}
