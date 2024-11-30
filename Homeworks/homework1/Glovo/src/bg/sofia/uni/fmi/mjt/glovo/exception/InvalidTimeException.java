package bg.sofia.uni.fmi.mjt.glovo.exception;

public class InvalidTimeException extends RuntimeException {
    public InvalidTimeException(String message) {
        super(message);
    }

    public InvalidTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
