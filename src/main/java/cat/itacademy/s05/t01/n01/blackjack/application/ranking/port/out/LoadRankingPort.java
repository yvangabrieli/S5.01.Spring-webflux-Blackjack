package cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.UUID;


public interface LoadRankingPort {
    Mono<List<Player>> loadPlayers(UUID gameId);
}
