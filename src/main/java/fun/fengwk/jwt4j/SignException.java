package fun.fengwk.jwt4j;

/**
 * @author fengwk
 */
public class SignException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SignException(String message) {
        super(message);
    }

    public SignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignException(Throwable cause) {
        super(cause);
    }

}
