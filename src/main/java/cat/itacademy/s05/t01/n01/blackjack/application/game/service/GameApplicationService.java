package cat.itacademy.s05.t01.n01.blackjack.application.game.service;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.in.*;
import cat.itacademy.s05.t01.n01.blackjack.application.game.port.out.GameRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.MoveType;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class GameApplicationService implements CreateGameUseCase, AddPlayerToGameUseCase, DeleteGameUseCase, GetGameUseCase, PlayGameUseCase {

    private final GameRepositoryPort gameRepository;

    public  GameApplicationService (GameRepositoryPort gameRepository){
        this.gameRepository = gameRepository;
    }

    @Override
    public UUID createGame() {
        Game newGame = new Game(UUID.randomUUID());
        gameRepository.save(newGame);
        return newGame.getId();
    }
    @Override
    public Game addPlayer(UUID gameId, String playerName, Money initialMoney){
       Game game = getGame(gameId);
       Player newPlayer = new Player(UUID.randomUUID(), playerName, initialMoney);
       game.addPlayer(newPlayer);
       gameRepository.save(game);
       return game;
    }


    @Override
    public void deleteGame(UUID gameId) {
        Game game = getGame(gameId);
        gameRepository.deleteById(game.getId());
    }

    @Override
    public Game getGame(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(()-> new GameNotFoundException(gameId));
    }

    @Override
    public Game play(UUID gameId, UUID playerId, MoveType moveType) {
        Game game = getGame(gameId);
        game.play(playerId, moveType);
        gameRepository.save(game);
        return game;
        }
}
