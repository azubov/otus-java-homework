package ru.otus.model;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public final class MethodSignature {

    private final String name;
    private final Class<?>[] parameterTypes;
    private final Class<?> returnType;

    public MethodSignature(final Method method) {
        this.name = method.getName();
        this.parameterTypes = method.getParameterTypes();
        this.returnType = method.getReturnType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodSignature methodSignature = (MethodSignature) o;

        if (!Objects.equals(name, methodSignature.name)) return false;
        if (!Arrays.equals(parameterTypes, methodSignature.parameterTypes)) return false;
        return Objects.equals(returnType, methodSignature.returnType);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(parameterTypes);
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        return result;
    }
}
