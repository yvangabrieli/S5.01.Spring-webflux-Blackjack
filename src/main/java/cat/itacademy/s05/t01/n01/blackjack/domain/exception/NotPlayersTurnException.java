package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

import java.util.UUID;

public class NotPlayersTurnException extends RuntimeException{
    public NotPlayersTurnException(){super();}
    public NotPlayersTurnException(UUID playerId){
        super("Is not the player's turn" + playerId);
    }
}
