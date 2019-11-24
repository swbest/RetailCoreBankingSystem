package util.exception;

/**
 *
 * @author sw_be
 */
public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
    }

    public InvalidLoginException(String msg) {
        super(msg);
    }
}
