package cat.itacademy.s05.t01.n01.blackjack.application.player.port.in;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;

import java.util.UUID;

public interface GetPlayerUseCase {
    Player getPlayer(UUID playerId);
}
