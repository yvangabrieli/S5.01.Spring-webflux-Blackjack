package cat.itacademy.s05.t01.n01.blackjack.application.player.port.in;

import java.util.UUID;

public interface DeletePlayerUseCase {
    void deletePlayer(UUID playerId);
}
