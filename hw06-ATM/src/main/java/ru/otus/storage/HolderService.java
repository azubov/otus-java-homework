package ru.otus.storage;

import ru.otus.ecxeption.NotEnoughBanknotesException;

public interface HolderService {

    void add(int quantity);
    BanknoteHolder withdraw(int quantity) throws NotEnoughBanknotesException;
    int getAmount();
    int getBanknotesQuantity(int amount);
    boolean hasBanknotes();
    boolean hasAmount(int requestedAmount);
}
