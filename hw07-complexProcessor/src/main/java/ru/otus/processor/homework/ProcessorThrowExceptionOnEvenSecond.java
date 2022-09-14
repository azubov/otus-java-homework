package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowExceptionOnEvenSecond implements Processor {

    private final TimeProvider time;

    public ProcessorThrowExceptionOnEvenSecond(TimeProvider time) {
        this.time = time;
    }

    @Override
    public Message process(Message message) {
        if (secondIsEven()) {
            throw new EvenSecondException();
        }
        return message;
    }

    private boolean secondIsEven() {
        return time.getSeconds() % 2 == 0;
    }
}
