package ru.otus.factory;

import ru.otus.storage.BanknoteHolder;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class BanknoteStorageFactory {

    private static final Comparator<BanknoteHolder> ORDER_BY_VALUE_DESC = Comparator.comparing(BanknoteHolder::getBanknoteValue).reversed();

    public static Set<BanknoteHolder> getStorage() {
        return new TreeSet<>(ORDER_BY_VALUE_DESC);
    }
}
