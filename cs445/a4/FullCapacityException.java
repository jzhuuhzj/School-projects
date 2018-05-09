package cs445.a4;

/**
 * A type of runtime exception thrown when the given expression is found to
 * be invalid
 */
@SuppressWarnings("serial")
public class FullCapacityException extends Exception {
    public FullCapacityException(String msg) {
        super(msg);
    }
}

