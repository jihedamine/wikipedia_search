package wikipedia.exceptions;

/**
 * Thrown when the application fails to initialize
 */
public class InitializationError extends RuntimeException {
    public InitializationError(String message, Throwable cause) {
        super(message, cause);
    }
}
