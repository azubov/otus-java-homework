package ru.otus.atm;

import ru.otus.ecxeption.NotEnoughBanknotesException;
import ru.otus.storage.BanknoteHolder;

import java.util.Set;

public interface AtmService {

    Set<BanknoteHolder> withdraw(int requestedAmount) throws NotEnoughBanknotesException;
    void deposit(Set<BanknoteHolder> banknotes);
    int getBalance();
}
