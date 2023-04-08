package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
abstract class BaseId {
    @Id
    protected final Long id;
}
