package ru.otus.factory;

import ru.otus.notes.Banknote;
import ru.otus.notes.Value;
import ru.otus.storage.BanknoteHolder;

import java.util.HashSet;
import java.util.Set;

public class BanknoteFactory {

    public static Set<BanknoteHolder> getEach(int quantity) {

        Set<BanknoteHolder> banknoteHolders = new HashSet<>();

        for(var value : Value.values()) {
            banknoteHolders.add(new BanknoteHolder(new Banknote(value), quantity));
        }
        return banknoteHolders;
    }
}
