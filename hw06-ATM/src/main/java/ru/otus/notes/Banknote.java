package ru.otus.notes;

public class Banknote {

    private final Value value;

    public Banknote(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Banknote banknote = (Banknote) o;

        return value == banknote.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
