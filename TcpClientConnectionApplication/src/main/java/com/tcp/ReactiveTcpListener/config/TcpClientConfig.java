package com.tcp.ReactiveTcpListener.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

@Configuration
public class TcpClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(TcpClientConfig.class);
//    private ReactiveTcpClient reactiveTcpClient;
//
//    public TcpConfig(@Lazy ReactiveTcpClient reactiveTcpClient) {
//        this.reactiveTcpClient = reactiveTcpClient;
//    }

    //    @Value("${tcp.server.host}")
    private String serverHost = "localhost";

    //    @Value("${tcp.server.port}")
    private int serverPort = 5000;

    //    @Value("${tcp.client.host}")
    private String clientHost = "localhost";

    //    @Value("${tcp.client.port}")
    private int clientPort = 5000;


    @Bean
    public TcpClient tcpClient() {
        return TcpClient.create()
                .host(clientHost)
                .port(clientPort)
                .wiretap(true)
                .doOnConnect(connection ->
                        logger.info("Attempting to connect to server at {}:{}", clientHost, clientPort));
    }

    @Bean
    public Mono<? extends Connection> connectionMono(TcpClient tcpClient) {
        return tcpClient.connect()
                .doOnNext(connection -> {
                    connection.inbound().receive()
                            .asString()
                            .doOnNext(response -> {
                                logger.info("Client received message: {}", response);

                            })
                            .subscribe();

                    logger.info("Client successfully connected to server.");
                })
                .doOnError(error -> {
                    logger.error("Error connecting to server: {}", error.getMessage());
                })
                .cache();
    }
}
