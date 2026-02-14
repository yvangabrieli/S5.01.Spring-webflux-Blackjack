package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.ranking;


import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.out.LoadRankingPort;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.player.SpringDataPlayerJpaRepository;
import reactor.core.publisher.Flux;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Repository
public class RankingRepositoryJpaAdapter implements LoadRankingPort {

    private final SpringDataPlayerJpaRepository repository;

    public RankingRepositoryJpaAdapter(SpringDataPlayerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<List<RankingEntry>> loadPlayers(UUID gameId) {
        return Mono.fromCallable(() -> repository.findAll())   // returns List<Player>
                .flatMapMany(Flux::fromIterable)               // Convert List<Player> → Flux<Player>
                .map(player -> new RankingEntry(
                        player.getId(),
                        player.getName(),
                        player.getBalance(),
                        0
                ))
                .sort(Comparator.comparing(RankingEntry::getBalance).reversed()) // descending
                .collectList();
    }
}

