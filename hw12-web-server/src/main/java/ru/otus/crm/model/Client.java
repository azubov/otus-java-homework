package ru.otus.crm.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client extends BaseId implements Cloneable {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "client")
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this(name);
        this.id = id;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this(id, name);
        this.address = address;
        addPhones(phones);
    }

    public Client(String name, Address address, List<Phone> phones) {
        this(name);
        this.address = address;
        addPhones(phones);
    }

    private void addPhones(List<Phone> phones) {
        phones.forEach(e -> e.setClient(this));
        this.phones.addAll(phones);
    }

    @Override
    public Client clone() {
        var addressClone = Optional.ofNullable(this.address).map(Address::clone).orElse(null);
        var phonesClone = this.phones.stream().map(Phone::clone).toList();
        return new Client(this.id, this.name, addressClone, phonesClone);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
