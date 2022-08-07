package ru.otus.proxy;

import ru.otus.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    private Ioc() {}

    public static <T> T createProxy(final T myClass) {
        var classInterfaces = myClass.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), classInterfaces, new LogInvocationHandler<>(myClass, Log.class));
    }

    static class LogInvocationHandler<T> implements InvocationHandler {

        private static final String LOG_MSG = "executed method: %s, param: %s";
        private final T myClass;
        private final Class<? extends Annotation> annotation;

        LogInvocationHandler(final T myClass, final Class<? extends Annotation> annotation) {
            this.myClass = myClass;
            this.annotation = annotation;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if (hasAnnotation(method)) return logging(myClass, method, args);
            return method.invoke(myClass, args);
        }

        private boolean hasAnnotation(final Method method) throws NoSuchMethodException {
            var methodImpl = getImpl(method);
            return methodImpl.isAnnotationPresent(annotation);
        }

        private Method getImpl(final Method method) throws NoSuchMethodException {
            return myClass.getClass().getMethod(method.getName(), method.getParameterTypes());
        }

        private Object logging(final T myClass, final Method method, final Object[] args) throws Throwable {
            System.out.printf(LOG_MSG + "%n", method.getName(), Arrays.toString(args));
            return method.invoke(myClass, args);
        }
    }
}
