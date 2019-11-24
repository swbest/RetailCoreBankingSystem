package util.exception;

/**
 *
 * @author sw_be
 */
public class InvalidAtmCardException extends Exception {
    
    public InvalidAtmCardException() {}
    
    public InvalidAtmCardException(String msg) {
        super(msg);
    }
}
