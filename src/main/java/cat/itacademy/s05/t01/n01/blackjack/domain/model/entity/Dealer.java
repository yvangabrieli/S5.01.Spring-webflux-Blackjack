package cat.itacademy.s05.t01.n01.blackjack.domain.model.entity;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;

import java.util.List;

public class Dealer {

    private final Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public void restoreHand(List<Card> cards) {
        hand.clear();
        cards.forEach(hand::addCard);
    }

}
