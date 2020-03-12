package wikipedia.exceptions;

public class JsonDeserializationException extends RuntimeException {
    public JsonDeserializationException(Class<?> clazz) {
        super("Cannot deserialize input to " + clazz);
    }

    public JsonDeserializationException(String message) {
        super(message);
    }
}
