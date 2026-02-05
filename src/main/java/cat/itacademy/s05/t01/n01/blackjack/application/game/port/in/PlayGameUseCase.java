package cat.itacademy.s05.t01.n01.blackjack.application.game.port.in;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.MoveType;

import java.util.UUID;

public interface PlayGameUseCase {
    public Game play(UUID gameId, UUID playerId, MoveType moveType);
}
