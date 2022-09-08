package ru.otus.storage;

import ru.otus.ecxeption.NotEnoughBanknotesException;
import ru.otus.notes.Banknote;
import ru.otus.notes.Value;

public class BanknoteHolder implements HolderService {

    private int quantity;
    private final Banknote banknote;

    public BanknoteHolder(final Banknote banknote, int quantity) {
        this.banknote = banknote;
        this.quantity = quantity;
    }

    @Override
    public void add(int requestedQuantity) {
        quantity += requestedQuantity;
    }

    @Override
    public BanknoteHolder withdraw(int requestedQuantity) throws NotEnoughBanknotesException {
        if (banknotesIsEnoughFor(requestedQuantity)) {
            quantity -= requestedQuantity;
            return new BanknoteHolder(banknote, requestedQuantity);
        } else {
            throw new NotEnoughBanknotesException();
        }
    }

    private boolean banknotesIsEnoughFor(int requestedQuantity) {
        return quantity - requestedQuantity >= 0;
    }

    @Override
    public int getAmount() {
        return banknote.getValue().getDenomination() * quantity;
    }

    @Override
    public int getBanknotesQuantity(int amount) {
        return amount / getBanknoteDenomination();
    }

    @Override
    public boolean hasBanknotes() {
        return getQuantity() > 0;
    }

    @Override
    public boolean hasAmount(int requestedAmount) {
        return requestedAmount - getAmount() >= 0;
    }

    private int getBanknoteDenomination() {
        return getBanknoteValue().getDenomination();
    }

    public int getQuantity() {
        return quantity;
    }

    public Value getBanknoteValue() {
        return banknote.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanknoteHolder that = (BanknoteHolder) o;

        return banknote.equals(that.banknote);
    }

    @Override
    public int hashCode() {
        return banknote.hashCode();
    }
}
