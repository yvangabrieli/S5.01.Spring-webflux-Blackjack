package cat.itacademy.s05.t01.n01.blackjack.web.exception;

import cat.itacademy.s05.t01.n01.blackjack.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public Mono<ResponseEntity<ApiError>> handleGameNotFound(GameNotFoundException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value())));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public Mono<ResponseEntity<ApiError>> handlePlayerNotFound(PlayerNotFoundException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value())));
    }

    @ExceptionHandler(InvalidMoveException.class)
    public Mono<ResponseEntity<ApiError>> handleInvalidMove(InvalidMoveException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value())));
    }

    @ExceptionHandler(NotPlayersTurnException.class)
    public Mono<ResponseEntity<ApiError>> handleNotPlayersTurn(NotPlayersTurnException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage(), HttpStatus.CONFLICT.value())));
    }

    @ExceptionHandler(GameFinishedException.class)
    public Mono<ResponseEntity<ApiError>> handleGameFinished(GameFinishedException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage(), HttpStatus.CONFLICT.value())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<ApiError>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(ObjectError::getDefaultMessage)
                .orElse("Invalid input");
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(message, HttpStatus.BAD_REQUEST.value())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ApiError>> handleIllegalArgument(IllegalArgumentException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value())));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiError>> handleGenericException(Exception ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }
}