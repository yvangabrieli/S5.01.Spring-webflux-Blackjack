package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.ranking;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in.GetRankingUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.player.SpringDataPlayerJpaRepository;
import reactor.core.publisher.Flux;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Repository
public class RankingRepositoryJpaAdapter implements GetRankingUseCase {

    private final SpringDataPlayerJpaRepository repository;

    public RankingRepositoryJpaAdapter(SpringDataPlayerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<List<RankingEntry>> getRanking(UUID gameId) {
        // Wrap blocking JPA call in a Mono
        return Mono.fromCallable(() -> repository.findAll())   // returns List<Player>
                .flatMapMany(Flux::fromIterable)               // Convert List<Player> → Flux<Player>
                .map(player -> new RankingEntry(
                        player.getId(),
                        player.getName(),
                        player.getBalance()
                ))
                .sort(Comparator.comparing(RankingEntry::getBalance).reversed()) // descending
                .collectList();                                 // Flux<RankingEntry> → Mono<List<RankingEntry>>
    }
}

