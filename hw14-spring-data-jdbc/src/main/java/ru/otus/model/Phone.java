package ru.otus.model;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("phone")
public class Phone extends BaseId {

    private final String number;
    private final Long clientId;

    public Phone(String number) {
        this(null, number, null);
    }

    @PersistenceCreator
    public Phone(Long id, String number, Long clientId) {
        super(id);
        this.number = number;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return number;
    }
}
