package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.utils.RunnerUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class Runner {

    private int executed;
    private int passed;
    private int failed;

    public void run(final String className) throws ClassNotFoundException {
        final Class<?> clazz = Class.forName(className);
        final Method[] methods = Objects.requireNonNull(clazz).getMethods();

        final Method before = RunnerUtils.getMethodByAnnotation(methods, Before.class);
        final Method[] tests = RunnerUtils.getFilteredMethodsByAnnotation(methods, Test.class);
        final Method after = RunnerUtils.getMethodByAnnotation(methods, After.class);

        for (var test : tests) {
            execute(clazz, before, test, after);
        }

        RunnerUtils.printResults(executed, passed, failed);
    }

    private <T> void execute(final Class<T> clazz, final Method before, final Method test, final Method after) {
        try {
            final T obj = clazz.getDeclaredConstructor().newInstance();

            if (Objects.nonNull(before)) before.invoke(obj);
            invoke(test, obj);
            if (Objects.nonNull(after)) after.invoke(obj);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ++executed;
        }
    }

    private <T> void invoke(final Method method, T obj) {
        try {
            method.invoke(obj);
            ++passed;
        } catch (Exception e) {
            System.out.println(e.getCause());
            ++failed;
        }
    }
}
