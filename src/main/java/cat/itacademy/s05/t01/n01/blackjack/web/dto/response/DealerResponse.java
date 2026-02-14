package cat.itacademy.s05.t01.n01.blackjack.web.dto.response;

import java.util.List;

public class DealerResponse {
    private List<String> hand;
    private int score;
    private boolean busted;

    public DealerResponse(List<String> hand, int score, boolean busted) {
        this.hand = hand;
        this.score = score;
        this.busted = busted;
    }

    public List<String> getHand() { return hand; }
    public int getScore() { return score; }
    public boolean isBusted() { return busted; }
}