package org.example;

import java.util.Objects;

public abstract class Animal implements Comparable<Animal> {
    public String color;
    public String name;
    public Integer age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract void speak();

    @Override
    public int compareTo(Animal other) {
        // Сравнение по возрасту (можно изменить логику по желанию)
        return Integer.compare(this.age, other.age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Animal)) return false;
        Animal animal = (Animal) obj;
        return age == animal.age && Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
