package nikinayzer.smichoffserver.endpoints.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoUserExistsException extends RuntimeException{
    public NoUserExistsException() {
        super();
    }
    public NoUserExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoUserExistsException(String message) {
        super(message);
    }
    public NoUserExistsException(Throwable cause) {
        super(cause);
    }
}
