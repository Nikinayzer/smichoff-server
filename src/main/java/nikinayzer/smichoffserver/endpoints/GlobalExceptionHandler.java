package nikinayzer.smichoffserver.endpoints;

import nikinayzer.smichoffserver.endpoints.dto.ErrorDTO;
import nikinayzer.smichoffserver.endpoints.exceptions.route.NoRouteFoundException;
import nikinayzer.smichoffserver.endpoints.exceptions.user.EmailAlreadyExistsException;
import nikinayzer.smichoffserver.endpoints.exceptions.user.NoUserExistsException;
import nikinayzer.smichoffserver.endpoints.exceptions.user.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDTO> buildErrorResponse(String message, String description, HttpStatus status) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .status(status.value())
                .message(message)
                .description(description)
                .timestamp(java.time.Instant.now())
                .build();
        return new ResponseEntity<>(errorDTO, status);
    }

    private ResponseEntity<String> buildStringResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(message, status);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return buildErrorResponse(ex.getMessage(), "Email already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return buildErrorResponse(ex.getMessage(), "Username already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoUserExistsException.class)
    public ResponseEntity<ErrorDTO> handleNoUserExistsException(NoUserExistsException ex) {
        return buildErrorResponse(ex.getMessage(), "No user found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRouteFoundException.class)
    public ResponseEntity<ErrorDTO> handleNoRouteFoundException(NoRouteFoundException ex) {
        return buildErrorResponse(ex.getMessage(), "No route found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleOtherExceptions(Exception ex) {
        return buildErrorResponse("An error occurred: " + ex.getMessage(), "Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
