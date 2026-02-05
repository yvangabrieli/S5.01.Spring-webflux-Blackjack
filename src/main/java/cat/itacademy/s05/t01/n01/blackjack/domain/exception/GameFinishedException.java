package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

public class GameFinishedException extends RuntimeException {
    public GameFinishedException() {
        super();
    }

    public GameFinishedException(String message) {
        super(message);
    }
}
