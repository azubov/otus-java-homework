package ru.otus.grpc.server;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.server.service.RemoteServiceImpl;

import java.io.IOException;

public class GRPCServer {
    private static final Logger log = LoggerFactory.getLogger(GRPCServer.class);

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var remoteService = new RemoteServiceImpl();
        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteService)
                .build();
        server.start();
        log.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
