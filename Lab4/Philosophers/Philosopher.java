import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher implements Runnable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private int id;
    private Lock leftFork;
    private Lock rightFork;
    private Random random;
    private int thinkingTimeMin;
    private int thinkingTimeMax;
    private int eatingTimeMax;
    private int gameOver ;

    public Philosopher(int id, Lock leftFork, Lock rightFork, int thinkingTimeMin, int thinkingTimeMax, int eatingTimeMax, int gameOverTime) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.random = new Random();
        this.thinkingTimeMin = thinkingTimeMin;
        this.thinkingTimeMax = thinkingTimeMax;
        this.eatingTimeMax = eatingTimeMax;
        this.gameOver = gameOverTime;
    }

    private void think() throws InterruptedException {
        System.out.printf(ANSI_YELLOW + "Философ " + id + " думает\n" + ANSI_RESET);
        TimeUnit.MILLISECONDS.sleep(random.nextInt(thinkingTimeMax - thinkingTimeMin + 1) + thinkingTimeMin);
    }

    private void eat() throws InterruptedException {
        System.out.printf(ANSI_GREEN + "Философ " + id + " ест\n" + ANSI_RESET);
        TimeUnit.MILLISECONDS.sleep(random.nextInt(eatingTimeMax));
    }

    @Override
    public void run() {
        try {
            while (true) {
                if(gameOver == 0){
                    System.out.printf(ANSI_RED  + "Философ " + id + " встает из-за стола \n" + ANSI_RESET);
                    Thread.currentThread().interrupt();
                    break;
                }else {
                    think();
                    gameOver--;
                    if (leftFork.tryLock()) {
                        try {
                            if (rightFork.tryLock()) {
                                try {
                                    eat();
                                    gameOver = 2;
                                } finally {
                                    rightFork.unlock();
                                }
                            }
                        } finally {
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

class Food {
    private static final int NUM_PHILOSOPHERS = 5;


    public static void main(String[] args) {
        // Timer timer = new Timer();
        Lock[] forks = new Lock[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Thread(new Philosopher(i, forks[i], forks[(i + 1) % NUM_PHILOSOPHERS], 1000, 3000, 500, 2));
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i].start();
        }
    }
}