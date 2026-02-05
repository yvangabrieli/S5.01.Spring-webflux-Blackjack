package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(){
        super();
    }
    public GameNotFoundException(UUID gameId){
        super ("Game not found with id: " + gameId);
    }
}
