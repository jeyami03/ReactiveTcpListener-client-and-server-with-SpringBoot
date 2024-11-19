package com.tcp.ReactiveTcpListener.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpServer;

@Configuration
public class TcpServerConfig {
    private static final Logger logger = LoggerFactory.getLogger(TcpServerConfig.class);

//    @Value("${tcp.server.host}")
    private String serverHost = "localhost";

//    @Value("${tcp.server.port}")
    private int serverPort = 5000;

    @Bean
    public TcpServer tcpServer() {
        return TcpServer.create()
                .host(serverHost)
                .port(serverPort)
                .doOnConnection(connection -> {
                    logger.info("Client connected: {}", connection.address());
                    String message = "Server: 14 Connection established!";
                    connection.outbound()
                            .sendString(Mono.just(message))
                            .then()
                            .subscribe();
                })
                .wiretap(true);
    }
}
