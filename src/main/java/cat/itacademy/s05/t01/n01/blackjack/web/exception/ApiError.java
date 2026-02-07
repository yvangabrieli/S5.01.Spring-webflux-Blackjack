package cat.itacademy.s05.t01.n01.blackjack.web.exception;

public class ApiError {
    private final int status;
    private final String message;
    public ApiError (String message, int status){
        this.status = status;
        this.message = message;
        }
        public String getMessage(){
        return message;
        }
        public int getStatus(){
        return status;
        }
}
