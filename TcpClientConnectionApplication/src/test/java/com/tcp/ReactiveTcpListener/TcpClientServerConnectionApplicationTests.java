package com.tcp.ReactiveTcpListener;

import com.tcp.ReactiveTcpListener.config.TcpClientConfig;
import com.tcp.ReactiveTcpListener.service.client.ReactiveTcpClientTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyOutbound;
import reactor.netty.tcp.TcpClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TcpClientServerConnectionApplicationTests {

    @Mock
    private Mono<Connection> connectionMono;

    @Mock
    private Connection connection;

    @Mock
    private NettyOutbound outbound;

    @InjectMocks
    private ReactiveTcpClientTest reactiveTcpClientTest;

    @Autowired
    private TcpClientConfig config;

    @Test
    void testTcpClientBeanCreation() {
        TcpClient tcpClient = config.tcpClient();
        assertNotNull(tcpClient);
    }

    @Test
    void testConnectionMonoBeanCreation() {
        Mono<? extends Connection> connectionMono = config.connectionMono(config.tcpClient());
        assertNotNull(connectionMono);
    }

    @Test
    void contextLoads() {
        Assertions.assertTrue(true); // Basic test to check context
    }

}
