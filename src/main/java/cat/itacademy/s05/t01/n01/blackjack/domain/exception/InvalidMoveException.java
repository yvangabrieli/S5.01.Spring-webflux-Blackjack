package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException() {

        super();
    }

    public InvalidMoveException(String message) {
        super(message);
    }
}

