package cat.itacademy.s05.t01.n01.blackjack.application.game.port.out;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface GameRepositoryPort {
    public void save(Game game);
    public Optional<Game> findById(UUID gameId);
    public void deleteById(UUID gameId);
}
