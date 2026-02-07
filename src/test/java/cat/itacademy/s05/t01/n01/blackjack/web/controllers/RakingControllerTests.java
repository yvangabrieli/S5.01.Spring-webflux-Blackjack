package cat.itacademy.s05.t01.n01.blackjack.web.controllers;


import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in.GetRankingUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import cat.itacademy.s05.t01.n01.blackjack.web.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(RankingController.class)
@Import(GlobalExceptionHandler.class)

public class RakingControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GetRankingUseCase getRankingUseCase;

    @Test
    void shouldReturnRanking() {
        // given
        List<RankingEntry> ranking = List.of(
                new RankingEntry(
                        UUID.randomUUID(),
                        "Alice",
                        BigDecimal.valueOf(1000)
                ),
                new RankingEntry(
                        UUID.randomUUID(),
                        "Bob",
                        BigDecimal.valueOf(500)
                )
        );

        when(getRankingUseCase.getRanking()).thenReturn(ranking);

        // when + then
        webTestClient.get()
                .uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Object.class)
                .hasSize(2);
    }
}

