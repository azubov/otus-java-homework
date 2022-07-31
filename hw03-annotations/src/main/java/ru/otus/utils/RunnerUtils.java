package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RunnerUtils {

    private static final String TESTS_FINISHED = "All tests are finished";
    private static final String TEST_RESULTS = "Executed: %d, Passed: %d, Failed: %d";

    public static Method getMethodByAnnotation(final Method[] methods, Class<? extends Annotation> annotation) {
        var filteredMethods = getFilteredMethodsByAnnotation(methods, annotation);
        return Arrays.stream(filteredMethods).findFirst().orElse(null);
    }

    public static Method[] getFilteredMethodsByAnnotation(final Method[] methods, Class<? extends Annotation> annotation) {
        return Arrays.stream(methods).filter(m -> m.isAnnotationPresent(annotation)).toArray(Method[]::new);
    }

    public static void printResults(int executed, int passed, int failed) {
        var results = String.format(TEST_RESULTS, executed, passed, failed);
        System.out.println("- - - - - - - - - - - -");
        System.out.println(TESTS_FINISHED);
        System.out.println(results);
    }
}
