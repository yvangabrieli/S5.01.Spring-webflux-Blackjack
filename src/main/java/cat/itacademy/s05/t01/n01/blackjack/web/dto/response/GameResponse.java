package cat.itacademy.s05.t01.n01.blackjack.web.dto.response;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.GameStatus;

import java.util.List;
import java.util.UUID;

public class GameResponse {
    private UUID gameId;
    private GameStatus status;
    private int currentPlayerIndex;
    private List<PlayerResponse> players;
    private DealerResponse dealer;
    private UUID winnerId;
    private LastMoveResponse lastMove;

    public GameResponse(UUID gameId, GameStatus status, int currentPlayerIndex,
                        List<PlayerResponse> players, DealerResponse dealer,
                        UUID winnerId, LastMoveResponse lastMove) {
        this.gameId = gameId;
        this.status = status;
        this.currentPlayerIndex = currentPlayerIndex;
        this.players = players;
        this.dealer = dealer;
        this.winnerId = winnerId;
        this.lastMove = lastMove;
    }

    public UUID getGameId() { return gameId; }
    public GameStatus getStatus() { return status; }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public List<PlayerResponse> getPlayers() { return players; }
    public DealerResponse getDealer() { return dealer; }
    public UUID getWinnerId() { return winnerId; }
    public LastMoveResponse getLastMove() { return lastMove; }
}