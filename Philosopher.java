package org.example;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher implements Runnable {
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String RED_COLOR = "\u001B[31m";
    private static final String YELLOW_COLOR = "\u001B[33m";
    private static final String GREEN_COLOR = "\u001B[32m";

    private int philosopherNumber;
    private Lock leftUtensil;
    private Lock rightUtensil;
    private Random randomGenerator;
    private int minThoughtTime;
    private int maxThoughtTime;
    private int maxMealTime;
    private int mealsLeft;

    public Philosopher(int number, Lock leftUtensil, Lock rightUtensil, int minThought, int maxThought, int maxMeal, int totalMeals) {
        this.philosopherNumber = number;
        this.leftUtensil = leftUtensil;
        this.rightUtensil = rightUtensil;
        this.randomGenerator = new Random();
        this.minThoughtTime = minThought;
        this.maxThoughtTime = maxThought;
        this.maxMealTime = maxMeal;
        this.mealsLeft = totalMeals;
    }

    private void reflect() throws InterruptedException {
        System.out.printf(YELLOW_COLOR + "Философ " + philosopherNumber + " размышляет\n" + RESET_COLOR);
        TimeUnit.MILLISECONDS.sleep(randomGenerator.nextInt(maxThoughtTime - minThoughtTime + 1) + minThoughtTime);
    }

    private void dine() throws InterruptedException {
        System.out.printf(GREEN_COLOR + "Философ " + philosopherNumber + " ест\n" + RESET_COLOR);
        TimeUnit.MILLISECONDS.sleep(randomGenerator.nextInt(maxMealTime));
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (mealsLeft == 0) {
                    System.out.printf(RED_COLOR + "Философ " + philosopherNumber + " покидает стол\n" + RESET_COLOR);
                    Thread.currentThread().interrupt();
                    break;
                } else {
                    reflect();
                    mealsLeft--;
                    if (leftUtensil.tryLock()) {
                        try {
                            if (rightUtensil.tryLock()) {
                                try {
                                    dine();
                                    mealsLeft = 2;
                                } finally {
                                    rightUtensil.unlock();
                                }
                            }
                        } finally {
                            leftUtensil.unlock();
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
        Lock[] utensils = new Lock[NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            utensils[i] = new ReentrantLock();
        }

        Thread[] philosophers = new Thread[NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            philosophers[i] = new Thread(new Philosopher(i, utensils[i], utensils[(i + 1) % NUMBER_OF_PHILOSOPHERS], 1000, 3000, 500, 2));
        }

        for (Thread philosopher : philosophers) {
            philosopher.start();
        }
    }
}
