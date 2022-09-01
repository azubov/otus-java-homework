package ru.otus.storage;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class BanknoteStorage {

    private static final Comparator<BanknoteHolder> ORDER_BY_VALUE_DESC = Comparator.comparing(BanknoteHolder::getBanknoteValue).reversed();
    private final Set<BanknoteHolder> storage = new TreeSet<>(ORDER_BY_VALUE_DESC);

    public Set<BanknoteHolder> getBanknotes() {
        return storage;
    }
}
