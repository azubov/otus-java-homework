package ru.otus.ecxeption;

public class NotEnoughBanknotesException extends Exception {

    public NotEnoughBanknotesException() {
        super("Sorry, not enough banknotes in this ATM!");
    }
}
