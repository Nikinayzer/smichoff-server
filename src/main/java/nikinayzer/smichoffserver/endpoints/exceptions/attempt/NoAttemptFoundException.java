package nikinayzer.smichoffserver.endpoints.exceptions.attempt;

public class NoAttemptFoundException extends RuntimeException{
    public NoAttemptFoundException() {
        super();
    }
    public NoAttemptFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoAttemptFoundException(String message) {
        super(message);
    }
    public NoAttemptFoundException(Throwable cause) {
        super(cause);
    }

}
