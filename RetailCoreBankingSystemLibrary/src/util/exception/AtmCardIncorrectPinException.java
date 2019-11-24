package util.exception;

/**
 *
 * @author sw_be
 */
public class AtmCardIncorrectPinException extends Exception {

    public AtmCardIncorrectPinException() {
    }

    public AtmCardIncorrectPinException(String msg) {
        super(msg);
    }
}
