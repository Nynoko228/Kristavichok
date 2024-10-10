package org.example;
import org.example.MyException;
import org.example.Example;

import java.util.*;

public class Main {
    private static void testString(String v1, String v2) {
        String result = "";
        long time = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            result += v1;
            result += v2;
        }
        time = System.nanoTime() - time;
        System.out.println(String.format("String %d %d", result.length(), time));
    }

    private static void testStringBuilder(String v1, String v2) {
        StringBuilder result = new StringBuilder();
        long time = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            result.append(v1);
            result.append(v2);
        }
        time = System.nanoTime() - time;
        System.out.println(String.format("StringBuilder %d %d", result.length(), time));
    }

    private static void testStringBuffer(String v1, String v2) {
        StringBuffer result = new StringBuffer();
        long time = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            result.append(v1);
            result.append(v2);
        }
        time = System.nanoTime() - time;
        System.out.println(String.format("StringBuffer %d %d", result.length(), time));
    }


    public static void main(String[] args) {
        testString("string1", "string2");
        testStringBuilder("string1", "string2");
        testStringBuffer("string1", "string2");
        Example exc = new Example();
        try {
            exc.throwExceptions();
        } catch (RuntimeException e) {
            System.out.println("Поймали ошибку выполнения: " + e.getMessage());
        } catch (MyException e) {
            System.out.println("Поймали свою ошибку: " + e.getMessage());
        }
        int sum = 0;
        if (args.length == 0) {
            System.out.println("Пожалуйста, введите числа для сложения.");
            return;
        }

        // Проход по всем переданным аргументам
        for (String arg : args) {
            try {
                // Преобразование строки в целое число
                int number = Integer.valueOf(arg);
                ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(0, 1));
                int size = arrayList.size();
                while (size < number){
                    arrayList.add(arrayList.get(size-1) + arrayList.get(size-2));
                    size++;
                }
                System.out.println(arrayList.get(size-1));
                sum += number; // Сложение

                Animal[] animals = new Animal[number];

                // Генерация случайных животных
                Random random = new Random();
                for (int i = 0; i < number; i++) {
                    String name = (random.nextBoolean() ? "Dog" : "Cat") + " " + (i + 1);
                    int age = random.nextInt(15); // Возраст от 0 до 14
                    System.out.println(age);
                    animals[i] = random.nextBoolean() ? new Dog(name, age) : new Cat(name, age);
                }

                // Сортировка массива животных
                java.util.Arrays.sort(animals);

                // Вывод отсортированных животных
                System.out.println("Sorted Animals:");
                for (Animal animal : animals) {
                    System.out.println(animal);
                }
                int summa = 0;
                ArrayList<Integer> randomList = new ArrayList<>();
                for (int i = 0; i < number; i++){
                    Random rnd = new Random();
                    randomList.add(rnd.nextInt(10000));
                    summa += randomList.get(randomList.size()-1);
                }
                float mean = (float) summa / randomList.size();
                Set<Integer> set = new HashSet<Integer>(randomList);
                System.out.println(set);
                try {
                    for (int i = 0; i < randomList.size(); i+=2){
                        int number1 = randomList.get(i);
                        int number2 = randomList.get(i + 1);
                        int summ = number1 + number2;
                        System.out.println(number1 + " + " + number2 + " = " + summ);
                    }
                }
                catch (Exception e){
                    System.out.println("Список должен содержать четное количество чисел.");
                }
            } catch (NumberFormatException e) {
                // Обработка ошибки, если аргумент не является числом
                System.out.println("Ошибка: '" + arg + "' не является числом. Пропускаем этот аргумент.");
            }
        }

        // Вывод результата
        System.out.println("Сумма чисел: " + sum);
        Dog dog = new Dog("Doge", 10);
//        dog.speak();
        dog.color = "blue";
//        System.out.println(dog);

//        System.out.println("Hello world!");
    }
}