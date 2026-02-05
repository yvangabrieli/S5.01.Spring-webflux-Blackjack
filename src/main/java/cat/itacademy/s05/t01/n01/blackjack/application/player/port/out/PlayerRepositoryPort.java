package cat.itacademy.s05.t01.n01.blackjack.application.player.port.out;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;


import java.util.Optional;
import java.util.UUID;

public interface PlayerRepositoryPort {
    void save(Player player);
    Optional<Player> findById(UUID playerId);
    void deleteById(UUID playerId);
}
