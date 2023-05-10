package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws Exception {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);
        // You code here...
        var configConstructor = configClass.getDeclaredConstructor();
        var configMethods = Arrays.asList(configClass.getDeclaredMethods());
        var configInstance = configConstructor.newInstance();

        var beanDefinitions = loadBeanDefinitions(configMethods);
        createContext(beanDefinitions, configInstance);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private List<Method> loadBeanDefinitions(List<Method> methods) {
        return methods.stream()
                .filter(this::isBean)
                .sorted(invocationOrder())
                .toList();
    }

    private boolean isBean(Method method) {
        return method.isAnnotationPresent(AppComponent.class);
    }

    private Comparator<Method> invocationOrder() {
        return Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order());
    }

    private void createContext(List<Method> beanDefinitions, Object configInstance) throws InvocationTargetException, IllegalAccessException {
        for (var beanDefinition : beanDefinitions) {
            var dependencies = createDependencies(beanDefinition);
            var bean = beanDefinition.invoke(configInstance, dependencies.toArray());
            var name = beanDefinition.getDeclaredAnnotation(AppComponent.class).name();
            addToContext(name, bean);
        }
    }

    private List<Object> createDependencies(Method beanDefinition) {
        var numberOfArgs = beanDefinition.getParameterCount();
        var argTypes = beanDefinition.getParameterTypes();

        List<Object> dependencies = new ArrayList<>(numberOfArgs);
        for (var type : argTypes) {
            dependencies.add(getAppComponent(type));
        }
        return dependencies;
    }

    private void addToContext(String name, Object bean) {
        if (appComponentsByName.containsKey(name)) {
            throw new IllegalArgumentException("%s already exist in the context".formatted(name));
        } else {
            appComponents.add(bean);
            appComponentsByName.put(name, bean);
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        if (isDuplicateAppComponent(componentClass)) {
            throw new IllegalArgumentException("%s already exist in the context".formatted(componentClass));
        }
        return (C) appComponents.stream()
                .filter(componentClass::isInstance).findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("%s not found in the context".formatted(componentClass)));
    }

    private <C> boolean isDuplicateAppComponent(Class<C> componentClass) {
        return appComponents.stream()
                .filter(component -> componentClass.isAssignableFrom(component.getClass()))
                .count() > 1;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) Optional.ofNullable(appComponentsByName.get(componentName))
                .orElseThrow(() ->
                        new IllegalArgumentException("%s not found in the context".formatted(componentName)));
    }
}
