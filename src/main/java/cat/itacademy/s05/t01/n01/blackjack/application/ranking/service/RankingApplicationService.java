package cat.itacademy.s05.t01.n01.blackjack.application.ranking.service;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in.GetRankingUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out.LoadRankingPort;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RankingApplicationService implements GetRankingUseCase {
    private final LoadRankingPort loadRankingPort;

    public RankingApplicationService(LoadRankingPort loadRankingPort) {
        this.loadRankingPort = loadRankingPort;
    }

    @Override
    public List<RankingEntry> getRanking() {
        List<Player> players = loadRankingPort.loadPlayers();
        return players.stream()
                .map(player -> new RankingEntry (player.getId(), player.getName(), player.getMoney().getAmount()))
                .sorted(Comparator.comparing(RankingEntry::getBalance).reversed())
                .toList();
    };
}
