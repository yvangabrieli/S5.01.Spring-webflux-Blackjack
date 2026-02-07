package cat.itacademy.s05.t01.n01.blackjack.application.ranking.service;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.out.GameRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out.LoadRankingPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;

import java.util.List;
import java.util.UUID;

public class LoadPlayerFromGamesAdapter implements LoadRankingPort {
    private final GameRepositoryPort gameRepositoryPort;
    private final UUID gameId;

    public LoadPlayerFromGamesAdapter(GameRepositoryPort gameRepositoryPort, UUID gameId){
        this.gameRepositoryPort = gameRepositoryPort;
        this.gameId = gameId;
    }

    @Override
    public List<Player> loadPlayers(){
        return gameRepositoryPort.findById(gameId)
                .map(Game::getPlayers)
                .stream()
                .flatMap(List::stream)
                .toList();
    }
}
