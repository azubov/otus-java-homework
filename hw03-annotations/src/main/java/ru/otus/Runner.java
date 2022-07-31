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
            execute(getInstance(clazz), before, test, after);
        }

        RunnerUtils.printResults(executed, passed, failed);
    }

    private <T> T getInstance(final Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> void execute(final T obj, final Method before, final Method test, final Method after) {
        try {
            if (Objects.nonNull(before)) invokeSimple(before, obj);
            invokeWithCounter(test, obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(after)) invokeSimple(after, obj);
            ++executed;
        }
    }

    private <T> void invokeSimple(final Method method, T obj) {
        try {
            method.invoke(obj);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    private <T> void invokeWithCounter(final Method method, T obj) {
        try {
            method.invoke(obj);
            ++passed;
        } catch (Exception e) {
            System.out.println(e.getCause());
            ++failed;
        }
    }
}
