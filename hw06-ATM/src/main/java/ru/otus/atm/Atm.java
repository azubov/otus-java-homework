package ru.otus.atm;

import ru.otus.ecxeption.NotEnoughBanknotesException;
import ru.otus.factory.BanknoteStorageFactory;
import ru.otus.storage.BanknoteHolder;

import java.util.HashSet;
import java.util.Set;

public class Atm implements AtmService {

    private final Set<BanknoteHolder> storage = BanknoteStorageFactory.getStorage();

    @Override
    public Set<BanknoteHolder> withdraw(int requestedAmount) throws NotEnoughBanknotesException {
        validateBalance(requestedAmount);

        Set<BanknoteHolder> withdrawal = new HashSet<>();

        for (var banknoteHolder : storage) {
            if (banknoteHolder.hasBanknotes() && banknoteHolder.hasAmount(requestedAmount)) {
                var quantity = banknoteHolder.getBanknotesQuantity(requestedAmount);
                if (quantity > 0) {
                    var withdrawHolder = banknoteHolder.withdraw(quantity);
                    requestedAmount -= withdrawHolder.getAmount();
                    withdrawal.add(withdrawHolder);
                }
            }
        }
        return withdrawal;
    }

    @Override
    public void deposit(Set<BanknoteHolder> banknoteHolders) {
        banknoteHolders.forEach(incomingHolder -> {
            if (storage.contains(incomingHolder)) {
                var persistedHolder = findInStorage(incomingHolder);
                persistedHolder.add(incomingHolder.getQuantity());
            } else {
                storage.add(incomingHolder);
            }
        });
    }

    @Override
    public int getBalance() {
        return storage.stream().mapToInt(BanknoteHolder::getAmount).sum();
    }

    private void validateBalance(int requestedAmount) throws NotEnoughBanknotesException {
        if (getBalance() < requestedAmount) {
            throw new NotEnoughBanknotesException();
        }
    }

    private BanknoteHolder findInStorage(BanknoteHolder incomingHolder) {
        return storage.stream()
                .filter(persistedHolder -> persistedHolder.getBanknoteValue().equals(incomingHolder.getBanknoteValue()))
                .findFirst().orElse(null);
    }
}
