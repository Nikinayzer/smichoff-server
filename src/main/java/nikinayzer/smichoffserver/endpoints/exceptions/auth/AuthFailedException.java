package nikinayzer.smichoffserver.endpoints.exceptions.auth;

public class AuthFailedException extends RuntimeException {
    public AuthFailedException() {
        super();
    }

    public AuthFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthFailedException(String message) {
        super(message);
    }

    public AuthFailedException(Throwable cause) {
        super(cause);
    }
}
