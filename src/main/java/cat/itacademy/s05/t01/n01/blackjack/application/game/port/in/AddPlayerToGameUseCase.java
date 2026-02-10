package cat.itacademy.s05.t01.n01.blackjack.application.game.port.in;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AddPlayerToGameUseCase {
    Mono<Game> addPlayer(UUID gameId, String playerName, Money initialMoney);
}
