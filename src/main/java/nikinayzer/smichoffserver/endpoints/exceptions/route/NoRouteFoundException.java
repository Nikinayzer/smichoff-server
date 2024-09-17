package nikinayzer.smichoffserver.endpoints.exceptions.route;

public class NoRouteFoundException extends RuntimeException {
    public NoRouteFoundException(String message) {
        super(message);
    }
}
