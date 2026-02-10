package cat.itacademy.s05.t01.n01.blackjack.application.game.port.in;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DeleteGameUseCase {
    Mono<Void> deleteGame(UUID gameId);
}
