package ru.otus.processor.homework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.model.Message;

@DisplayName("Класс ProcessorThrowExceptionOnEvenSecond должен")
class ProcessorThrowExceptionOnEvenSecondTest {

    private TimeProvider timeMock;
    private Message messageMock;

    @BeforeEach
    void setUp() {
        timeMock = Mockito.mock(TimeProvider.class);
        messageMock = Mockito.mock(Message.class);
    }

    @DisplayName("Выбрасывать исключение в четную секунду")
    @Test
    void process_throwsExceptionOnEvenSecond() {
        var evenSecond = 2;
        Mockito.when(timeMock.getSeconds()).thenReturn(evenSecond);

        var processor = new ProcessorThrowExceptionOnEvenSecond(timeMock);

        Assertions.assertThatExceptionOfType(EvenSecondException.class)
                .isThrownBy(() -> processor.process(messageMock))
                .withMessage("You have hit an even second!");
    }

    @DisplayName("Не выбрасывать исключение в нечетную секунду")
    @Test
    void process_doesNotThrowExceptionOnOddSecond() {
        var oddSecond = 1;
        Mockito.when(timeMock.getSeconds()).thenReturn(oddSecond);

        var processor = new ProcessorThrowExceptionOnEvenSecond(timeMock);

        Assertions.assertThatNoException().isThrownBy(() -> processor.process(messageMock));
    }
}
