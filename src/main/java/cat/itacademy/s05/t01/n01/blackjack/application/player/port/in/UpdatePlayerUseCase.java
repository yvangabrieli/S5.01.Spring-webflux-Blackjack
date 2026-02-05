package cat.itacademy.s05.t01.n01.blackjack.application.player.port.in;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;

import java.util.UUID;

public interface UpdatePlayerUseCase {
    Player updatePlayer(UUID playerId, String newPlayerName, Money newMoney );
}
