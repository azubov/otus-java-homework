package ru.otus.crm.model;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "phone")
public class Phone extends BaseId {

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    void setClient(Client client) {
        this.client = client;
    }
}
