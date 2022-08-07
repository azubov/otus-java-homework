package ru.otus;

import ru.otus.proxy.Ioc;
import ru.otus.service.CalculationService;
import ru.otus.service.CalculationServiceImpl;

public class Main {
    public static void main(String... args) {

        CalculationService service = Ioc.createProxy(new CalculationServiceImpl());

        service.calculation(1);
        service.calculation(2, 3);
        service.calculation(4, 5, "six");
    }
}
