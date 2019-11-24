package util.exception;

/**
 *
 * @author sw_be
 */
public class AtmCardNumberExistException extends Exception {
    
    public AtmCardNumberExistException() {}
    
    public AtmCardNumberExistException(String msg) {
        super(msg);
    }
}
