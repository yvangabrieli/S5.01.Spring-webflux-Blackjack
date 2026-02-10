package cat.itacademy.s05.t01.n01.blackjack.application.ranking.service;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.out.GameRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out.LoadRankingPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public class LoadPlayerFromGamesAdapter implements LoadRankingPort {
    private final GameRepositoryPort gameRepositoryPort;

    public LoadPlayerFromGamesAdapter(GameRepositoryPort gameRepositoryPort){
        this.gameRepositoryPort = gameRepositoryPort;

    }

    @Override
    public Mono<List<Player>> loadPlayers(UUID gameId){
        return gameRepositoryPort.findById(gameId)
                .map(Game::getPlayers);

    }
}
