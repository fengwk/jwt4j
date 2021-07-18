package fun.fengwk.jwt4j;

/**
 * @author fengwk
 */
public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

}
