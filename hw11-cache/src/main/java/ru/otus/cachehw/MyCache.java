package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    private final Map<String, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        var weakKey = getWeak(key);
        cache.put(weakKey, value);
        notifyListeners(key, value, ListenerAction.PUT);
    }

    @Override
    public void remove(K key) {
        var weakKey = getWeak(key);
        V value = cache.get(weakKey);
        cache.remove(weakKey);
        notifyListeners(key, value, ListenerAction.REMOVE);
    }

    @Override
    public V get(K key) {
        var weakKey = getWeak(key);
        V value = cache.get(weakKey);
        notifyListeners(key, value, ListenerAction.GET);
        return value;
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private String getWeak(K key) {
        return "key: " + key;
    }

    private void notifyListeners(K key, V value, ListenerAction action) {
        listeners.forEach(listener -> listener.notify(key, value, action.toString()));
    }
}
