package ru.otus.grpc.client.service;

import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.Utils;
import ru.otus.grpc.generated.RemoteServiceGrpc;
import ru.otus.grpc.generated.RequestMessage;

public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ManagedChannel channel;
    private final ResponseStreamObserver responseObserver;

    public ClientServiceImpl(ManagedChannel channel) {
        this.channel = channel;
        this.responseObserver = new ResponseStreamObserver();
    }

    @Override
    public void printSequence() {
        var remoteService = RemoteServiceGrpc.newStub(channel);
        var requestMessage = RequestMessage.newBuilder().setFirstValue(0).setLastValue(30).build();
        remoteService.generateSequence(requestMessage, responseObserver);

        int val = 0;
        for (int i = 1; i <= 50; ++i) {
            val = val + 1 + responseObserver.getValue();
            log.info("{}) currentValue:{}", i, val);
            Utils.sleep(1);
        }
    }
}
