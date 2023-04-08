package ru.otus.model;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("address")
public class Address extends BaseId {

    private final String street;
    private final Long clientId;

    public Address(String street) {
        this(null, street, null);
    }

    @PersistenceCreator
    public Address(Long id, String street, Long clientId) {
        super(id);
        this.street = street;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return street;
    }
}
