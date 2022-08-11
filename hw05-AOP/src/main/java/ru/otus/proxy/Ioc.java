package ru.otus.proxy;

import ru.otus.annotation.Log;
import ru.otus.model.Logger;
import ru.otus.model.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Ioc {

    private Ioc() {}

    public static <T> T createProxy(final T myClass) {
        var classInterfaces = myClass.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), classInterfaces, new LogInvocationHandler<>(myClass, Log.class));
    }

    static class LogInvocationHandler<T> implements InvocationHandler {

        private final T myClass;
        private final Set<MethodSignature> annotatedMethods;

        LogInvocationHandler(final T myClass, final Class<? extends Annotation> annotation) {
            this.myClass = myClass;
            this.annotatedMethods = getAnnotatedMethods(myClass, annotation);
        }

        private Set<MethodSignature> getAnnotatedMethods(final T myClass, final Class<? extends Annotation> annotation) {
            return Arrays.stream(myClass.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(annotation))
                    .map(MethodSignature::new)
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if (isAnnotated(method)) {
                return logAndInvoke(myClass, method, args);
            }
            return method.invoke(myClass, args);
        }

        private boolean isAnnotated(final Method method) {
            var signature = new MethodSignature(method);
            return annotatedMethods.contains(signature);
        }

        private Object logAndInvoke(final T myClass, final Method method, final Object[] args) throws Throwable {
            Logger.log(method.getName(), args);
            return method.invoke(myClass, args);
        }
    }
}
