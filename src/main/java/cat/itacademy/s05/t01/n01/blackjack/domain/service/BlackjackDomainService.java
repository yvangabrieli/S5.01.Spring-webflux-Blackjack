package cat.itacademy.s05.t01.n01.blackjack.domain.service;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.entity.Dealer;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Score;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BlackjackDomainService {

    public Optional<Player> determineWinner (List<Player> players, Dealer dealer){
        int dealerScore = scoreOrZero(dealer.getHand().score());

        return players.stream()
                .filter(player -> !player.getHand().score().isBusted())
                .max(Comparator.comparingInt(p -> p.getHand().score().getValue()))
                .filter(player -> player.getHand().score().getValue() > dealerScore );
    }
    public boolean playerBeatsDealer(Player player, Dealer dealer) {
        int playerScore = scoreOrZero(player.getHand().score());
        int dealerScore = scoreOrZero(dealer.getHand().score());

        return playerScore > dealerScore;
    }
    public boolean dealerShouldHit(Dealer dealer) {
        return dealer.getHand().score().getValue() < 17;
    }
    private int scoreOrZero(Score score) {
        return score.isBusted() ? 0 : score.getValue();
    }
}
