package ru.otus.notes;

public enum Value {

    ONE(1),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    ONE_HUNDRED(100);

    private final int denomination;

    Value(int num) {
        denomination = num;
    }

    public int getDenomination() {
        return denomination;
    }
}
