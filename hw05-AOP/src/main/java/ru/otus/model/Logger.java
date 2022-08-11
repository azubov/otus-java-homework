package ru.otus.model;

import java.util.Arrays;

public final class Logger {

    private static final String LOG_MSG = "executed method: %s, param: %s";

    private Logger() {}

    public static void log(final String name, final Object[] args) {
        System.out.printf(LOG_MSG + "%n", name, Arrays.toString(args));
    }
}
