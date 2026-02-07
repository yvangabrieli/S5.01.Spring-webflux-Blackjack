package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.ranking;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in.GetRankingUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.player.SpringDataPlayerJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RankingRepositoryJpaAdapter implements GetRankingUseCase {

    private final SpringDataPlayerJpaRepository repository;

    public RankingRepositoryJpaAdapter(SpringDataPlayerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RankingEntry> getRanking() {
        return repository.findAll()
                .stream()
                .map(p -> new RankingEntry(
                        p.getId(),
                        p.getName(),
                        p.getBalance()
                ))
                .toList();
    }
}

