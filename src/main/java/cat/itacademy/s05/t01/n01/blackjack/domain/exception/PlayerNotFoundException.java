package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

import java.util.UUID;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(){
        super();
    }
    public PlayerNotFoundException (UUID playerId){
        super("Not found player with id: " + playerId);
    }
}
