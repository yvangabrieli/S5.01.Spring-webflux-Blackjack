package cat.itacademy.s05.t01.n01.blackjack.domain.model.entity;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Score score() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            total += card.getRank().getValue();
            if (card.getRank() == Card.Rank.ACE) aces++;
        }

        // Handle Aces as 1 or 11
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return new Score(total);
    }

    public boolean isBusted() {
        return score().getValue() > 21;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public void clear() {
        cards.clear();
    }
}
