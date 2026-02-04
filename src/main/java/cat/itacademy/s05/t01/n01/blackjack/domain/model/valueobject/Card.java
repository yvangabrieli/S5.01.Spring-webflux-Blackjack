package cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject;
import java.util.Objects;

public class Card {

    public enum Suit {
        HEARTS("♥"),
        DIAMONDS ("♦"),
        CLUBS ("♣"),
        SPADES ("♠");
        private String symbol;
        Suit(String symbol){
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    public enum Rank {
        TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5,"5"), SIX(6,"6"), SEVEN(7, "7"), EIGHT(8,"8"),
        NINE(9, "9"), TEN(10,"10"), JACK(10,"10"), QUEEN(10,"10"), KING(10,"10"), ACE(11,"11"); // ACE can be 1 or 11, handled in Score

        private final int value;
        private String symbol;
        Rank(int value, String symbol) {
            this.value = value;
            this.symbol = symbol;
        }
        public int getValue() { return value; }
        public String getSymbol(){return symbol;}
    }

    private final Suit suit;
    private final Rank rank;


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        return rank.getSymbol() + " of " + suit.getSymbol();
    }
}

