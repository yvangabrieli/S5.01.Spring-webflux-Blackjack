package cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;

import java.util.List;

public interface GetRankingUseCase {
    List<RankingEntry> getRanking();
}
