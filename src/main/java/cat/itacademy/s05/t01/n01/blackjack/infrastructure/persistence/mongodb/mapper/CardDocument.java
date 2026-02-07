package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;

public class CardDocument {
    private String suit;
    private String rank;

    public static CardDocument fromDomain(Card card) {
        CardDocument doc = new CardDocument();
        doc.suit = card.getSuit().name();
        doc.rank = card.getRank().name();
        return doc;
    }

    public Card toDomain() {
        return new Card(
                Card.Suit.valueOf(suit),
                Card.Rank.valueOf(rank)
        );
    }
}