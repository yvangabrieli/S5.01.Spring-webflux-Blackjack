package cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    private Deck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public List<Card> getCards(){
        return List.copyOf(cards);
    }

    public Deck shuffle() {
        List<Card> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled, new Random());
        return new Deck(shuffled);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(0);
    }
    public static Deck fromCards(List<Card> cards){
        return new Deck(cards);
    }

    public int remainingCards() {
        return cards.size();
    }
}
