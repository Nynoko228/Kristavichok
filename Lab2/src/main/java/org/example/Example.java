package org.example;

public class Example {
    public void throwExceptions() throws MyException {
        // Выбрасываем RuntimeException
        throw new RuntimeException("Ошибка времени выполнения.");
    }
}
