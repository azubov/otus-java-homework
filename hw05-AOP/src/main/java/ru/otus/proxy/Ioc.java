package ru.otus.proxy;

import ru.otus.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Ioc {

    private Ioc() {}

    public static <T> T createProxy(final T myClass) {
        var classInterfaces = myClass.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), classInterfaces, new LogInvocationHandler<>(myClass, Log.class));
    }

    static class LogInvocationHandler<T> implements InvocationHandler {

        private static final String LOG_MSG = "executed method: %s, param: %s";
        private final T myClass;
        private final List<Method> annotatedMethods;

        LogInvocationHandler(final T myClass, final Class<? extends Annotation> annotation) {
            this.myClass = myClass;
            this.annotatedMethods = getAnnotatedMethods(myClass, annotation);
        }

        private List<Method> getAnnotatedMethods(final T myClass, final Class<? extends Annotation> annotation) {
            return Arrays.stream(myClass.getClass().getMethods()).filter(m -> m.isAnnotationPresent(annotation)).toList();
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if (isAnnotated(method)) {
                return logging(myClass, method, args);
            }
            return method.invoke(myClass, args);
        }

        private boolean isAnnotated(final Method method) {
            Predicate<Method> signature = m ->
                    m.getName().equals(method.getName())
                    && Arrays.toString(m.getParameterTypes()).equals(Arrays.toString(method.getParameterTypes()))
                    && m.getReturnType().equals(method.getReturnType());
            return annotatedMethods.stream().anyMatch(signature);
        }

        private Object logging(final T myClass, final Method method, final Object[] args) throws Throwable {
            System.out.printf(LOG_MSG + "%n", method.getName(), Arrays.toString(args));
            return method.invoke(myClass, args);
        }
    }
}
