package com.tcp.ReactiveTcpListener.controller;

import com.tcp.ReactiveTcpListener.service.client.ReactiveTcpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(TcpController.class)
public class TcpControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveTcpClient tcpClient;

    @Test
    void testSendMessageToServerEndpoint() {
        when(tcpClient.sendMessageWithResponse("testMessage"))
                .thenReturn(Mono.just("testResponse"));

        webTestClient.get().uri("/tcp/sendToServer?message=testMessage")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Server response: testResponse");
    }
}

