package cat.itacademy.s05.t01.n01.blackjack.application.game.port.in;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;


import java.util.Optional;
import java.util.UUID;

public interface GetGameUseCase {
    public Game getGame(UUID gameId);
}
