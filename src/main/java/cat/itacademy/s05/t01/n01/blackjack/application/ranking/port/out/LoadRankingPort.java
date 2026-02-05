package cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;

import java.util.List;

public interface LoadRankingPort {
    List<Player> loadPlayers();
}
