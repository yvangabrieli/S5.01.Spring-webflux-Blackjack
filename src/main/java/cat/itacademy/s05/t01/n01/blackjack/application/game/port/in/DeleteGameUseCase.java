package cat.itacademy.s05.t01.n01.blackjack.application.game.port.in;

import java.util.UUID;

public interface DeleteGameUseCase {
    public void deleteGame(UUID gameId);
}
