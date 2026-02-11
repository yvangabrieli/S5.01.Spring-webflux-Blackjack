package cat.itacademy.s05.t01.n01.blackjack.application.ranking.service;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in.GetRankingUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out.LoadRankingPort;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.UUID;

            @Service
            @Qualifier("rankingApplicationService")
            public class RankingApplicationService implements GetRankingUseCase {
                private final LoadRankingPort loadRankingPort;

                public RankingApplicationService(LoadRankingPort loadRankingPort) {
                    this.loadRankingPort = loadRankingPort;
                }

                @Override
                public Mono<List<RankingEntry>> getRanking(UUID gameId) {
                    return loadRankingPort.loadPlayers(gameId);
                }

            }

