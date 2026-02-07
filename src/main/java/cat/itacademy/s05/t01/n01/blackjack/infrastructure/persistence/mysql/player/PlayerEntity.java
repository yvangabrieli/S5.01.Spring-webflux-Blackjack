package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    private UUID id;

    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    protected PlayerEntity(){}

    public PlayerEntity (UUID id, String name, BigDecimal balance){
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
    public UUID getId() {return id;}
    public String getName() {return name;}
    public BigDecimal getBalance() {return balance;}
}
