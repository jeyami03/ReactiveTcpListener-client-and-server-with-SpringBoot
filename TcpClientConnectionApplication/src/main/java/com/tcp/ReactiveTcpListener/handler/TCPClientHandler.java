package com.tcp.ReactiveTcpListener.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

@Component
public class TCPClientHandler {
    private static final Logger logger = LoggerFactory.getLogger(TCPClientHandler.class);
    private final Mono<? extends Connection> connectionMono;

    @Autowired
    public TCPClientHandler(Mono<? extends Connection> connectionMono) {
        this.connectionMono = connectionMono;
    }

    public void listenToServerMessages() {
        connectionMono.flatMapMany(connection ->
                processServerMessages(connection))
                .doOnComplete(() -> {
                    logger.info("Completed listening to server messages.");
                }).doOnError(error -> {
                    logger.error("Error while processing server messages: {}", error.getMessage());
                }).subscribe();
    }


    private Flux<String> processServerMessages(Connection connection) {
        return connection.inbound().receive().asString().doOnNext(message -> {
            logger.info("Client received message: {}", message);
        }).doOnError(error -> {
            logger.error("Error in message stream: {}", error.getMessage());
        });
    }
}
