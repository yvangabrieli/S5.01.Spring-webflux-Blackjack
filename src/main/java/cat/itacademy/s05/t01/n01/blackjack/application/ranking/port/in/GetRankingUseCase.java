package cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface GetRankingUseCase {
    Mono<List<RankingEntry>> getRanking(UUID gameId);
}
