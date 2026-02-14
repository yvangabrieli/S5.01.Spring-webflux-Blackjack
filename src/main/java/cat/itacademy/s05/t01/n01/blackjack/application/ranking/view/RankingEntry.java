package cat.itacademy.s05.t01.n01.blackjack.application.ranking.view;

import java.math.BigDecimal;
import java.util.UUID;


public class RankingEntry {
    private final UUID playerId;
    private final String playerName;
    private final BigDecimal balance;
    private int wins;

    public RankingEntry(UUID playerId, String playerName, BigDecimal balance, int wins) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.balance = balance;
        this.wins = wins;
    }

    public UUID getPlayerId() { return playerId; }
    public String getPlayerName() { return playerName; }
    public BigDecimal getBalance() { return balance; }
    public int getWins(){return wins;}
}

