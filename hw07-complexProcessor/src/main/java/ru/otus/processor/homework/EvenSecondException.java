package ru.otus.processor.homework;

public class EvenSecondException extends RuntimeException {

    private static final String MSG = "You have hit an even second!";

    public EvenSecondException() {
        super(MSG);
        System.err.println(MSG);
    }
}
