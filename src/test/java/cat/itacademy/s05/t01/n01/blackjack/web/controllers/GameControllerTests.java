package cat.itacademy.s05.t01.n01.blackjack.web.controllers;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.in.*;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.InvalidMoveException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.MoveType;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.GameResponse;
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


import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Import(GlobalExceptionHandler.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(GameController.class)
public class GameControllerTests {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private  CreateGameUseCase createGameUseCase;
    @MockBean
    private  GetGameUseCase getGameUseCase ;
    @MockBean
    private  PlayGameUseCase playGameUseCase;
    @MockBean
    private  DeleteGameUseCase deleteGameUseCase;
    @MockBean
    private  AddPlayerToGameUseCase addPlayerToGameUseCase;

    @Test
    void shouldCreateGameAndReturnGameResponse(){
    UUID gameId = randomUUID();
        Game game = new Game(gameId);

    when(createGameUseCase.createGame())
            .thenReturn(gameId);
    when(getGameUseCase.getGame(gameId)).thenReturn(game);

    webTestClient.post()
            .uri("/games")
    .exchange()
    .expectStatus().isOk()
    .expectBody(GameResponse.class)
            .value(response ->  {
                assertEquals(gameId, response.getGameId());
                assertEquals(GameStatus.NOT_STARTED, response.getStatus());
                assertEquals(0, response.getCurrentPlayerIndex());
                assertNull(response.getLastMove());
            });
    }

    @Test
    void shouldReturn404WhenGameNotFound(){
        UUID gameId = randomUUID();

        when(createGameUseCase.createGame())
                .thenThrow(new GameNotFoundException(gameId));

        webTestClient.post()
                .uri("/games")
                .exchange()
                .expectStatus().isNotFound() // Expect 404
                .expectBody()
                .jsonPath("$.message").isEqualTo("Game not found with id: " + gameId )
                .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    void shouldAddPlayerWithPrimitiveMoney() {
        UUID gameId = randomUUID();

        // Represent money as int in JSON
        String json = """
        {
            "name": "Alice",
            "initialMoney": 100
        }
        """;

        Game game = new Game(gameId);

        when(addPlayerToGameUseCase.addPlayer(eq(gameId), eq("Alice"), any(Money.class)))
                .thenReturn(game);

        webTestClient.post()
                .uri("/games/{id}/players", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(response -> {
                    assertEquals(gameId, response.getGameId());
                    assertEquals(GameStatus.NOT_STARTED, response.getStatus());
                });
    }

    @Test
    void shouldSetGameStatusToInProgressWhenPlayerPlaysValidMove(){
        UUID gameId = randomUUID();
        UUID playerId = randomUUID();
        Game game = new Game(gameId);
        Player player = new Player(playerId, "John", new Money(200) );
        game.addPlayer(player);
        game.start();

        when(playGameUseCase.play(gameId, playerId, MoveType.HIT))
                .thenReturn(game);
        webTestClient.post()
                .uri("/games/{gameId}/play", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("playerId", playerId.toString(), "moveType", "HIT"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("IN_PROGRESS");
    }
    @Test
    void shouldReturn400WhenInvalidMove(){
        UUID gameId = randomUUID();
        UUID playerId = randomUUID();
        when(playGameUseCase.play(gameId, playerId, MoveType.HIT))
                .thenThrow(new InvalidMoveException("Cannot play move, game not in progress" ));

        webTestClient.post()
                .uri("/games/{gameId}/play", gameId)
                .bodyValue(Map.of("playerId", playerId, "moveType", "HIT"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Cannot play move, game not in progress")
                .jsonPath("$.status").isEqualTo(400);
    }
@Test
    void shouldDeleteGameAndReturnNoContent(){
       UUID gameId = randomUUID();

       webTestClient.delete()
               .uri("/games/{gameId}", gameId)
               .exchange()
               .expectStatus().isNoContent()
               .expectBody().isEmpty();
       verify(deleteGameUseCase).deleteGame(gameId);
}


}