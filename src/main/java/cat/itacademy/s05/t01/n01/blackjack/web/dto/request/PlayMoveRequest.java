package cat.itacademy.s05.t01.n01.blackjack.web.dto.request;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.MoveType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PlayMoveRequest {
    @NotNull (message = "playerId is required")
    private UUID playerId;
    @NotNull (message = "moveType is required")
    private MoveType moveType;

    public UUID getPlayerId(){
        return playerId;
    }
    public MoveType getMoveType(){
        return moveType;
    }
}
