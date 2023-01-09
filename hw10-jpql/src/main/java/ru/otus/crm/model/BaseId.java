package ru.otus.crm.model;

import lombok.Getter;

import javax.persistence.*;

@MappedSuperclass
@Getter
abstract class BaseId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;
}
