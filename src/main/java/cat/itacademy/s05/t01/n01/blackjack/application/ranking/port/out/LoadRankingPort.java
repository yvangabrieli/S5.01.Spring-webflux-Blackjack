package cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.UUID;


public interface LoadRankingPort {
    Mono<List<RankingEntry>> loadPlayers(UUID gameId);
}
