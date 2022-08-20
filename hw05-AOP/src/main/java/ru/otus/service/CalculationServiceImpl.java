package ru.otus.service;

import ru.otus.annotation.Log;

public class CalculationServiceImpl implements CalculationService {

    @Override
    public void calculation(int param1) {
        System.out.println(param1);
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.printf("%d + %d %n", param1, param2);
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.printf("%d + %d + %s %n", param1, param2, param3);
    }
}
