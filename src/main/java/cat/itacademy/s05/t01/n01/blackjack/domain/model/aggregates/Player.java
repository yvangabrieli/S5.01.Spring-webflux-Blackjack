package cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.entity.Hand;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @Column (columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name ="hand_id")
    private Hand hand;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "balance"))
    private Money money;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "current_bet"))
    private Money currentBet = new Money(0);

    protected Player(){
        this.hand = new Hand();
        this.currentBet = new Money(0);
    }
    public Player(UUID id, String name, Money initialMoney) {
        if (id == null) throw new IllegalArgumentException("Player ID cannot be null");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Player name cannot be blank");
        if (initialMoney == null) throw new IllegalArgumentException("Initial money cannot be null");

        this.id = id;
        this.name = name;
        this.hand = new Hand();
        this.money = initialMoney;
        this.currentBet = new Money(0);
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
        if (card == null) throw new IllegalArgumentException("Card cannot bu null");
        hand.addCard(card);
    }

    public void clearHand() {
        hand.clear();
    }
    public void setMoney(Money money) {
        if (money == null) throw new IllegalArgumentException("Money cannot be null");
        this.money = money;
    }
    public void addMoney (Money amount){
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        this.money = this.money.add(amount);
    }

    public void subtractMoney (Money amount){
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        this.money = this.money.subtract(amount);
    }

    public void placeBet (Money amount){
        if (amount == null) throw new IllegalArgumentException("Bet amount cannot be null");
        if (!amount.isPositive()) throw new IllegalArgumentException("Bet must be positive");
        this.money = this.money.subtract(amount);
        this.currentBet = amount;
    }

    public void clearBet(){
        this.currentBet = new Money(0);
    }

    public boolean hasBet() {
        return !currentBet.isZero();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
