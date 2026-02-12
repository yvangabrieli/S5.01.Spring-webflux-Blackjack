package cat.itacademy.s05.t01.n01.blackjack.application.game.service;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.in.*;
import cat.itacademy.s05.t01.n01.blackjack.application.game.port.out.GameRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.MoveType;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class GameApplicationService implements
        CreateGameUseCase,
        AddPlayerToGameUseCase,
        DeleteGameUseCase,
        GetGameUseCase,
        PlayGameUseCase {

    private final GameRepositoryPort gameRepository;

    public GameApplicationService(GameRepositoryPort gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Mono<UUID> createGame() {
        Game newGame = new Game(UUID.randomUUID());
        return gameRepository.save(newGame)
                .thenReturn(newGame.getId());
    }

    @Override
    public Mono<Game> addPlayer(UUID gameId, String playerName, Money initialMoney) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .map(game -> {
                    Player player = new Player(UUID.randomUUID(), playerName, initialMoney);
                    game.addPlayer(player.getId(), playerName, initialMoney);
                    return game;
                })
                .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Void> deleteGame(UUID gameId) {
        return getGame(gameId)
                .flatMap(game -> gameRepository.deleteById(game.getId()));
    }

    @Override
    public Mono<Game> getGame(UUID gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)));
    }

    @Override
    public Mono<Game> play(UUID gameId, UUID playerId, MoveType moveType) {
        return getGame(gameId)
                .map(game -> {
                    game.play(playerId, moveType);
                    return game;
                })
                .flatMap(gameRepository::save);
    }
}
