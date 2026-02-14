package cat.itacademy.s05.t01.n01.blackjack.web.dto.response;

import java.util.List;
import java.util.UUID;

public class PlayerInGameResponse {
    private UUID id;
    private String name;
    private double balance;
    private List<String> hand;  // ← Card representations
    private int score;  // ← Hand score
    private boolean busted;

    public PlayerInGameResponse(UUID id, String name, double balance,
                                List<String> hand, int score, boolean busted) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.hand = hand;
        this.score = score;
        this.busted = busted;
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public List<String> getHand() { return hand; }
    public int getScore() { return score; }
    public boolean isBusted() { return busted; }
}