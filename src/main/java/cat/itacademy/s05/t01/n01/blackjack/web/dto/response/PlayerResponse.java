package cat.itacademy.s05.t01.n01.blackjack.web.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class PlayerResponse {
    private UUID id;
    private String name;
    private BigDecimal balance;

    public PlayerResponse(UUID id, String name, BigDecimal balance){
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
    public UUID getId(){return id;}
    public String getName() {return name;}
    public BigDecimal getBalance(){ return balance;}
}
