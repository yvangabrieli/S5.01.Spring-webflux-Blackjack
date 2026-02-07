package cat.itacademy.s05.t01.n01.blackjack.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HealthController.class)
public class HealthControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnOkHealthStatus() {
        webTestClient.get()
                .uri("/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("OK");
    }
}
