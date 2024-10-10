package org.example;

public class Cat extends Animal{
    public Cat(String name, int age) {
        super(name, age);
    }
    @Override
    public void speak(){
        System.out.println("Мяу-мяу");
    }

    @Override
    public String toString(){
        return "Cat " + age;
    }
}
