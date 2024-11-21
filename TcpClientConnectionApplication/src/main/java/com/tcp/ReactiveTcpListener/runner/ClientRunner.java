package com.tcp.ReactiveTcpListener.runner;

import com.tcp.ReactiveTcpListener.config.TcpClientConfig;
import com.tcp.ReactiveTcpListener.handler.TCPClientHandler;
import com.tcp.ReactiveTcpListener.service.client.ReactiveTcpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;


@Component
public class ClientRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(TcpClientConfig.class);

    private final Mono<? extends Connection> connectionMono;
    private final TCPClientHandler tcpClientHandler;

    @Autowired
    public ClientRunner(Mono<? extends Connection> connectionMono, TCPClientHandler tcpClientHandler) {
        this.connectionMono = connectionMono;
        this.tcpClientHandler = tcpClientHandler;
    }

    @Override
    public void run(String... args) {
        tcpClientHandler.listenToServerMessages();
        connectionMono.subscribe(connection -> {
            logger.info("ClientRunner: Connection successful");
        }, error -> {
            logger.error("ClientRunner: Connection failed - {}", error.getMessage());
        });
    }
}
