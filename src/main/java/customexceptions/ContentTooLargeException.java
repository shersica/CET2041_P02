package customexceptions;

public class ContentTooLargeException extends RuntimeException {
    public ContentTooLargeException(String message) {
        super(message);
    }
}
