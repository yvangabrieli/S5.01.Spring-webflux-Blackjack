package cat.itacademy.s05.t01.n01.blackjack.web.controllers;


import cat.itacademy.s05.t01.n01.blackjack.application.game.port.in.*;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
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
    private final GetGameUseCase getGameUseCase ;
    private final PlayGameUseCase playGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final AddPlayerToGameUseCase addPlayerToGameUseCase;

    @PostMapping
    public Mono<GameResponse> createGame(){
        UUID gameId = createGameUseCase.createGame();
        Game game = getGameUseCase.getGame(gameId);
        return Mono.just(GameResponseMapper.from(game));
    }

    @GetMapping("/{gameId}")
    public Mono<GameResponse> getGame(@PathVariable UUID gameId){
        Game game = getGameUseCase.getGame(gameId);
        return Mono.just(GameResponseMapper.from(game));
    }

    @PostMapping ("/{gameId}/players")
    public Mono<GameResponse> addPlayer (
            @PathVariable UUID gameId,
            @RequestBody AddPlayerRequest request
            ){
        Game game =  addPlayerToGameUseCase.addPlayer(gameId, request.getName(), request.getInitialMoney());
        return Mono.just(GameResponseMapper.from(game));
    }

    @PostMapping("/{gameId}/play")
    public Mono<GameResponse> playGame(
            @PathVariable UUID gameId,
            @Valid @RequestBody PlayMoveRequest request
    ){
        Game game = playGameUseCase.play(gameId, request.getPlayerId(),request.getMoveType());
        return Mono.just(GameResponseMapper.from(game));
    }

    @DeleteMapping ("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable UUID gameId){
        deleteGameUseCase.deleteGame(gameId);
    }
}
