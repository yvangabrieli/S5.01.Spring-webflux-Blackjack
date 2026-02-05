package cat.itacademy.s05.t01.n01.blackjack.domain.service;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.entity.Dealer;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackDomainServiceTest {
    private BlackjackDomainService service;
    private Dealer dealer;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setup() {
        service = new BlackjackDomainService();
        dealer = new Dealer();
        player1 = new Player(null, "Alice",new Money(600));
        player2 = new Player(null, "Bob", new Money(500));
    }

    @Test
    void testDealerShouldHit() {
        dealer.addCard(new Card (Card.Suit.HEARTS, Card.Rank.TEN)); // 10
        dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SIX)); // 6 -> total 16
        assertTrue(service.dealerShouldHit(dealer));

        dealer.addCard(new Card(Card.Suit.SPADES ,Card.Rank.TWO)); // total 18
        assertFalse(service.dealerShouldHit(dealer));
    }

    @Test
    void testPlayerBeatsDealer() {
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN)); // 17

        player1.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));
        player1.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE)); // 21

        assertTrue(service.playerBeatsDealer(player1, dealer));
        assertFalse(service.playerBeatsDealer(player2, dealer));
    }

    @Test
    void testDetermineWinner() {
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.NINE));
        dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN)); // dealer 16

        player1.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        player1.addCard(new Card(Card.Suit.SPADES, Card.Rank.SEVEN)); // 17

        player2.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE));
        player2.addCard(new Card(Card.Suit.SPADES, Card.Rank.SIX)); // 15

        Optional<Player> winner = service.determineWinner(List.of(player1, player2), dealer);

        assertTrue(winner.isPresent());
        assertEquals(player1, winner.get());
    }

    @Test
    void testDetermineWinnerWithBustedPlayer() {
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN)); // 17

        player1.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));
        player1.addCard(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));
        player1.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TWO)); // 22 -> busted

        Optional<Player> winner = service.determineWinner(List.of(player1), dealer);
        assertTrue(winner.isEmpty());
    }
}

