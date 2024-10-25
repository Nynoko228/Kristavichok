import java.util.Random;


class Car extends Thread {
    private static final Parking parking = new Parking();
    private final int num; // Номер автомобиля

    // Конструктор класса Car
    public Car(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        parking.park(this);
    }

    // Геттер для получения номера автомобиля
    public int getNum() {
        return num;
    }
}

class Parking {
    private static final Integer[] PLACES = new Integer[5]; // Всего 5 мест на парковке

    public void park(Car car) {
        int place;
        // Используем синхронизацию, чтобы 2 автомобиля не попытались занять 1 место на парковке
        synchronized (PLACES) {
            // Ищем свободно место на парковке
            while ((place = findEmptyPlace()) < 0) {
                try {
                    // Если свободного места нет, то ждём
                    System.out.println("Car " + car.getNum() + " waiting");
                    PLACES.wait();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            PLACES[place] = car.getNum(); // Машина занимает парковочное место
            System.out.println("Car " + car.getNum() + " take place " + place);
        }

        // Симуляция времени, проведенного на парковке
        try {
            Thread.sleep(new Random().nextInt(500, 10000)); // 500 - 1000 мс
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Спустя 0.5 - 1 сек автомобиль освобождает место
        synchronized (PLACES) {
            PLACES[place] = null;
            System.out.println("Car " + car.getNum() + " leave place " + place);
            PLACES.notify();
        }
    }
    // Функция для поиска свободного места
    private int findEmptyPlace() {
        for (int i = 0; i < PLACES.length; i++) {
            if (PLACES[i] == null) {
                return i; // Возвращаем номер свободного места
            }
        }
        return -1; // Все места заняты
    }
}

// Основной поток, который генерирует поток-автомобиль
public class ParkingLotSimulation {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) { // Генерируем 100 автомобилей
            try {
                Thread.sleep(random.nextInt(1000)); // Генерация через случайные промежутки времени до 1 сек
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Car(i).start(); // Запускаем поток для генерации автомобиля
        }
    }
}