package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class Example {

    @Before
    public void before_each() {
        System.out.println("Before");
    }

    @After
    public void after_each() {
        System.out.println("After");
    }

    @Test
    public void first_test() {
        System.out.println("First Test Success");
    }

    @Test
    public void second_test() {
        throw new RuntimeException("Second Test Failed");
    }

    @Test
    public void third_test() {
        System.out.println("Third Test Success");
    }
}
