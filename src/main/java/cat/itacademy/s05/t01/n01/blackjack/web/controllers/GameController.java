package cat.itacademy.s05.t01.n01.blackjack.web.controllers;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.in.*;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.request.AddPlayerRequest;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.request.PlayMoveRequest;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.GameResponse;
import cat.itacademy.s05.t01.n01.blackjack.web.mapper.GameResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final PlayGameUseCase playGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final AddPlayerToGameUseCase addPlayerToGameUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponse> createGame() {
        return createGameUseCase.createGame()
                .flatMap(getGameUseCase::getGame)
                .map(GameResponseMapper::from);
    }

    @GetMapping("/{gameId}")
    public Mono<GameResponse> getGame(@PathVariable UUID gameId) {
        return getGameUseCase.getGame(gameId)
                .map(GameResponseMapper::from);
    }

    @PostMapping("/{gameId}/players")
    public Mono<GameResponse> addPlayer(
            @PathVariable UUID gameId,
            @Valid @RequestBody AddPlayerRequest request
    ) {
        return addPlayerToGameUseCase
                .addPlayer(gameId, request.getName(), request.getInitialMoney())
                .map(GameResponseMapper::from);
    }

    @PostMapping("/{gameId}/play")
    public Mono<GameResponse> playGame(
            @PathVariable UUID gameId,
            @Valid @RequestBody PlayMoveRequest request
    ) {
        return playGameUseCase
                .play(gameId, request.getPlayerId(), request.getMoveType())
                .map(GameResponseMapper::from);
    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGame(@PathVariable UUID gameId) {
        return deleteGameUseCase.deleteGame(gameId);
    }
}
