package cat.itacademy.s05.t01.n01.blackjack.web.dto.response;

import java.util.UUID;

public class LastMoveResponse {
    private UUID playerId;
    private String move;
    private String cardDrawn;

    public LastMoveResponse(UUID playerId, String move, String cardDrawn){
        this.playerId = playerId;
        this.move = move;
        this.cardDrawn = cardDrawn;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getMove() {
        return move;
    }
    public String getCardDrawn() {
        return cardDrawn;
    }


}
