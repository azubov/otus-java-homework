package ru.otus.grpc.client;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.client.service.ClientServiceImpl;

public class GRPCClient {
    private static final Logger log = LoggerFactory.getLogger(GRPCClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        log.info("Client is staring...");
        var clientService = new ClientServiceImpl(channel);
        clientService.printSequence();
        channel.shutdown();
    }
}
