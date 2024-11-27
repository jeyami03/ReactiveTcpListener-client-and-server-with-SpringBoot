package com.tcp.ReactiveTcpListener.handler;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.test.StepVerifier;

import static reactor.core.publisher.Mono.when;
import static org.mockito.Mockito.*;

class TCPClientHandlerTest {

    @Test
    void testListenToServerMessages() {
        Connection mockConnection = mock(Connection.class);
        when(mockConnection.inbound().receive().asString())
                .thenReturn(Flux.just("Message1", "Message2"));

        TCPClientHandler handler = new TCPClientHandler(Mono.just(mockConnection));

        StepVerifier.create(handler.processServerMessages(mockConnection))
                .expectNext("Message1")
                .expectNext("Message2")
                .verifyComplete();
    }
}
