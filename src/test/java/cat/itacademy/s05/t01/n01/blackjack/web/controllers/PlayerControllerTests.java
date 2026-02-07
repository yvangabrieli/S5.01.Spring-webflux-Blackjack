package cat.itacademy.s05.t01.n01.blackjack.web.controllers;


import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.DeletePlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.GetPlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.UpdatePlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.request.UpdatePlayerRequest;
import cat.itacademy.s05.t01.n01.blackjack.web.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.mockito.Mockito.*;


@Import(GlobalExceptionHandler.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(PlayerController.class)

public class PlayerControllerTests {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UpdatePlayerUseCase updatePlayerUseCase;
    @MockBean
    private GetPlayerUseCase getPlayerUseCase;
    @MockBean
    private DeletePlayerUseCase deletePlayerUseCase;


        @Test
        void shouldReturnPlayerWhenPlayerExists() {
            UUID playerId = UUID.randomUUID();
            Player player = new Player(playerId, "Anna", new Money(250));

            when(getPlayerUseCase.getPlayer(playerId)).thenReturn(player);

            webTestClient.get()
                    .uri("/players/{playerId}", playerId)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        void shouldUpdatePlayerAndReturnUpdatedPlayer () {
            UUID playerId = UUID.randomUUID();
            String json = """
            {
                "name" : "New Name",
                "money" : 500
            }
            """;

            Player updatedPlayer = new Player(playerId, "New Name", new Money(500));

            when(updatePlayerUseCase.updatePlayer(
                    eq(playerId),
                    eq("New Name"),
                    any(Money.class)
            )).thenReturn(updatedPlayer);

            webTestClient.put()
                    .uri("/players/{playerId}", playerId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        void shouldReturnBadRequestWhenUpdatePlayerRequestIsInvalid () {
            UUID playerId = UUID.randomUUID();
            UpdatePlayerRequest invalidRequest =
                    new UpdatePlayerRequest(null,new Money(-100));

            webTestClient.put()
                    .uri("/players/{playerId}", playerId)
                    .bodyValue(invalidRequest)
                    .exchange()
                    .expectStatus().isBadRequest();

            verify(updatePlayerUseCase, never())
                    .updatePlayer(any(), any(), any());
        }

        @Test
        void shouldDeletePlayerAndReturnNoContent() {
            UUID playerId = UUID.randomUUID();

            doNothing().when(deletePlayerUseCase).deletePlayer(playerId);

            webTestClient.delete()
                    .uri("/players/{playerId}", playerId)
                    .exchange()
                    .expectStatus().isNoContent();

            verify(deletePlayerUseCase).deletePlayer(playerId);
        }
    }
