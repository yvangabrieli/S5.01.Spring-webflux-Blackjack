package cat.itacademy.s05.t01.n01.blackjack.application.game.port.out;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import reactor.core.publisher.Mono;


import java.util.UUID;


public interface GameRepositoryPort {
    Mono<Game> save(Game game);
    Mono<Game> findById(UUID gameId);
    Mono<Void> deleteById(UUID gameId);
}
