package cat.itacademy.s05.t01.n01.blackjack.web.controllers;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.in.*;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.InvalidMoveException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.MoveType;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.GameResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebFluxTest(GameController.class)
class GameControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean private CreateGameUseCase createGameUseCase;
    @MockBean private GetGameUseCase getGameUseCase;
    @MockBean private PlayGameUseCase playGameUseCase;
    @MockBean private DeleteGameUseCase deleteGameUseCase;
    @MockBean private AddPlayerToGameUseCase addPlayerToGameUseCase;
    @MockBean private StartGameUseCase startGameUseCase;

    @Test
    void shouldCreateGameAndReturnGameResponse() {
        UUID gameId = randomUUID();
        Game game = new Game(gameId);

        when(createGameUseCase.createGame()).thenReturn(Mono.just(gameId));
        when(getGameUseCase.getGame(gameId)).thenReturn(Mono.just(game));

        webTestClient.post().uri("/games").exchange()
                .expectStatus().isCreated()
                .expectBody(GameResponse.class)
                .value(response -> {
                    assertEquals(gameId, response.getGameId());
                    assertEquals(GameStatus.NOT_STARTED, response.getStatus());
                });
    }

    @Test
    void shouldReturn404WhenGameNotFound() {
        UUID gameId = randomUUID();
        when(getGameUseCase.getGame(gameId))
                .thenReturn(Mono.error(new GameNotFoundException(gameId)));

        webTestClient.get().uri("/games/{id}", gameId).exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldAddPlayer() {
        UUID gameId = randomUUID();
        Game game = new Game(gameId);

        when(addPlayerToGameUseCase.addPlayer(eq(gameId), eq("Alice"), any(Money.class)))
                .thenReturn(Mono.just(game));

        webTestClient.post()
                .uri("/games/{id}/players", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("name", "Alice", "initialMoney", Map.of("amount", 100)))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldStartGame() {
        UUID gameId = randomUUID();
        Game game = new Game(gameId);
        game.addPlayer(randomUUID(), "Alice", new Money(500));
        game.start();

        when(startGameUseCase.startGame(gameId)).thenReturn(Mono.just(game));

        webTestClient.post().uri("/games/{gameId}/start", gameId).exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.status").isEqualTo("IN_PROGRESS");
    }

    @Test
    void shouldPlayMove() {
        UUID gameId = randomUUID();
        UUID playerId = randomUUID();
        Game game = new Game(gameId);
        game.addPlayer(playerId, "Alice", new Money(500));
        game.start();

        when(playGameUseCase.play(gameId, playerId, MoveType.HIT))
                .thenReturn(Mono.just(game));

        webTestClient.post()
                .uri("/games/{gameId}/play", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("playerId", playerId.toString(), "moveType", "HIT"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturn400WhenInvalidMove() {
        UUID gameId = randomUUID();
        UUID playerId = randomUUID();

        when(playGameUseCase.play(gameId, playerId, MoveType.HIT))
                .thenReturn(Mono.error(new InvalidMoveException("Game not in progress")));

        webTestClient.post()
                .uri("/games/{gameId}/play", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("playerId", playerId.toString(), "moveType", "HIT"))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void shouldDeleteGame() {
        UUID gameId = randomUUID();
        when(deleteGameUseCase.deleteGame(gameId)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/games/{gameId}", gameId).exchange()
                .expectStatus().isNoContent();

        verify(deleteGameUseCase).deleteGame(gameId);
    }
}