package ru.otus.grpc.client.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.generated.ResponseMessage;

import java.util.concurrent.atomic.AtomicInteger;

public class ResponseStreamObserver implements StreamObserver<ResponseMessage> {
    private static final Logger log = LoggerFactory.getLogger(ResponseStreamObserver.class);

    private final AtomicInteger currentValue = new AtomicInteger(0);

    @Override
    public void onNext(ResponseMessage value) {
        var newValue = value.getValue();
        log.info("new value:{}", newValue);
        currentValue.set(newValue);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
        log.info("request completed");
    }

    public int getValue() {
        var value = currentValue.get();
        currentValue.set(0);
        return value;
    }
}
