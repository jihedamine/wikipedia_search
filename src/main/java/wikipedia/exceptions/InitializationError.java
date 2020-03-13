package wikipedia.exceptions;

public class InitializationError extends RuntimeException {
    public InitializationError(String message, Throwable cause) {
        super(message, cause);
    }
}
