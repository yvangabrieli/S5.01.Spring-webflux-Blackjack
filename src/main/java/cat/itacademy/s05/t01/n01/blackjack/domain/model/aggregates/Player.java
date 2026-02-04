package cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.entity.Hand;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;


import java.util.UUID;

public class Player {

    private final UUID id;
    private String name;
    private final Hand hand;
    private Money money;
    private Money currentBet = new Money(0);

    public Player(UUID id, String name, Money initialMoney) {
        this.id = id;
        this.name = name;
        this.hand = new Hand();
        this.money = initialMoney;
    }


    public UUID getId() { return id; }
    public String getName() { return name; }
    public Hand getHand() { return hand; }
    public Money getMoney(){return money;}
    public Money getCurrentBet(){
        return currentBet;
    }

    public void updateName(String newName) {
    if (newName == null || newName.isBlank()){
        throw new IllegalArgumentException("New name cannot be blank or null");
    }
        this.name = newName;

    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public void addMoney (Money amount){
        this.money = this.money.add(amount);
    }

    public void subtractMoney (Money amount){
        this.money = this.money.subtract(amount);
    }

    public void placeBet (Money amount){
        this.money = this.money.subtract(amount);
        this.currentBet = amount;
    }

    public void clearBet(){
        this.currentBet = new Money(0);
    }


}
