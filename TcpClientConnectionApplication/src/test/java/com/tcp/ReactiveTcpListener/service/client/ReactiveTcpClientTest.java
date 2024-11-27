package com.tcp.ReactiveTcpListener.service.client;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyOutbound;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class ReactiveTcpClientTest {

    @Test
    void testSendMessageWithResponse() {
        Connection mockConnection = mock(Connection.class);
        NettyOutbound mockOutbound = mock(NettyOutbound.class);

        when(mockConnection.outbound()).thenReturn(mockOutbound);
        when(mockOutbound.sendString(any(Mono.class))).thenReturn(mockOutbound);
        when(mockOutbound.then()).thenReturn(Mono.empty()); // Simulates completion

        ReactiveTcpClient client = new ReactiveTcpClient(Mono.just(mockConnection));
        Mono<String> response = client.sendMessageWithResponse("Hello");

        StepVerifier.create(response)
                .expectNext("Hello")
                .verifyComplete();
    }
}


