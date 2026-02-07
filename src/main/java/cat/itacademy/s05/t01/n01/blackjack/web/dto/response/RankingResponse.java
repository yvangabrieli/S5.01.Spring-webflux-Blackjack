package cat.itacademy.s05.t01.n01.blackjack.web.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class RankingResponse {
    private final UUID playerId;
    private final String playerName;
    private final BigDecimal balance;

    public RankingResponse(UUID playerId, String playerName, BigDecimal balance) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.balance = balance;
    }

    public UUID getPlayerId() { return playerId; }
    public String getPlayerName() { return playerName; }
    public BigDecimal getBalance() { return balance; }
}