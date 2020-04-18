package exception;

/**
 * @author wujingyi
 */
public class NoSuchKeyException extends RuntimeException {
    public NoSuchKeyException() {
        super();
    }

    public NoSuchKeyException(String message) {
        super("key = " + message);
    }
}
