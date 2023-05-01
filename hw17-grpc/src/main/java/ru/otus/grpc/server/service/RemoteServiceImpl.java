package ru.otus.grpc.server.service;

import io.grpc.stub.StreamObserver;
import ru.otus.grpc.Utils;
import ru.otus.grpc.generated.RemoteServiceGrpc;
import ru.otus.grpc.generated.RequestMessage;
import ru.otus.grpc.generated.ResponseMessage;

import java.util.List;
import java.util.stream.Stream;

public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void generateSequence(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {
        List<ResponseMessage> responseList = generateResponseMessages(request.getFirstValue(), request.getLastValue());
        responseList.forEach(response -> {
            responseObserver.onNext(response);
            Utils.sleep(2);
        });
        responseObserver.onCompleted();
    }

    private List<ResponseMessage> generateResponseMessages(int begin, int end) {
        int limit = end - begin + 1;
        return Stream.iterate(begin, val -> ++val)
                .map(val -> ResponseMessage.newBuilder().setValue(val).build())
                .limit(limit)
                .toList();
    }
}
