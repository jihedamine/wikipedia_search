package wikipedia.exceptions;

/**
 * Thrown when failing to deserialize a Json string into a Java Object
 */
public class JsonDeserializationException extends RuntimeException {
    public JsonDeserializationException(Class<?> clazz) {
        super("Cannot deserialize input to " + clazz);
    }

    public JsonDeserializationException(String message) {
        super(message);
    }
}
