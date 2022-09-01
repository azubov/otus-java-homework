package ru.otus;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.atm.Atm;
import ru.otus.ecxeption.NotEnoughBanknotesException;
import ru.otus.factory.BanknoteFactory;
import ru.otus.storage.BanknoteHolder;
import ru.otus.storage.BanknoteStorage;

import java.util.Set;

@DisplayName("Класс AtmService должен")
class AtmServiceTest {

    private Atm atm;
    private Set<BanknoteHolder> banknotes;

    @BeforeEach
    void setup() {
        atm = new Atm(new BanknoteStorage());
        banknotes = BanknoteFactory.getEach(1);
    }

    @Test
    @DisplayName("принимать деньги")
    void depositTest() {
        var banknotesSum = getSum(banknotes);
        var balanceBeforeDeposit = atm.getBalance();

        atm.deposit(banknotes);
        var balanceAfterDeposit = atm.getBalance();

        Assertions.assertThat(balanceBeforeDeposit).isZero();
        Assertions.assertThat(balanceAfterDeposit).isEqualTo(banknotesSum);
    }

    @Test
    @DisplayName("выбросить NotEnoughBanknotesException когда недостаточно банкнот")
    void notEnoughBanknotesExceptionTest() {
        int request = 12;
        var banknotesSum = getSum(banknotes);
        var balanceBeforeDeposit = atm.getBalance();

        atm.deposit(banknotes);
        var balanceAfterDeposit = atm.getBalance();

        Assertions.assertThat(balanceBeforeDeposit).isZero();
        Assertions.assertThatExceptionOfType(NotEnoughBanknotesException.class)
                .isThrownBy(() -> atm.withdraw(request))
                .withMessage("Sorry, not enough banknotes in this ATM!");
        Assertions.assertThat(balanceAfterDeposit).isEqualTo(banknotesSum);
    }

    @Test
    @DisplayName("снимать деньги")
    void withdrawTest() {
        int request = 12;
        var balanceBeforeDeposit = atm.getBalance();

        atm.deposit(banknotes);
        atm.deposit(banknotes);
        var balanceAfterDeposit = atm.getBalance();

        var withdraw = org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> atm.withdraw(request));
        var withdrawSum = getSum(withdraw);
        var balanceAfterWithdraw = atm.getBalance();

        Assertions.assertThat(balanceBeforeDeposit).isZero();
        Assertions.assertThat(withdrawSum).isEqualTo(request);
        var balanceDifference = balanceAfterDeposit - balanceAfterWithdraw;
        Assertions.assertThat(balanceDifference).isEqualTo(request);
    }

    private int getSum(Set<BanknoteHolder> banknotes) {
        return banknotes.stream().mapToInt(BanknoteHolder::getAmount).sum();
    }
}
