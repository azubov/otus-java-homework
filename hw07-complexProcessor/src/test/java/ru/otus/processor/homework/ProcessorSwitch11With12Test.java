package ru.otus.processor.homework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

@DisplayName("Класс ProcessorSwitch11With12 должен")
class ProcessorSwitch11With12Test {

    @DisplayName("Поменять местами значения полей 11 и 12")
    @Test
    void process_shouldSwitchFields() {
        var field11Value = "field11Value";
        var field12Value = "field12Value";
        var message = new Message.Builder(1L).field11(field11Value).field12(field12Value).build();

        var processor = new ProcessorSwitch11With12();
        var result = processor.process(message);

        Assertions.assertThat(result.getField11()).isEqualTo(field12Value);
        Assertions.assertThat(result.getField12()).isEqualTo(field11Value);
    }
}
