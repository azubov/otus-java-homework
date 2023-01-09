package ru.otus.crm.model;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "address")
public class Address extends BaseId {

    @Column(name = "street")
    private String street;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }
}
