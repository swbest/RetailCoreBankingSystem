package util.exception;

/**
 *
 * @author sw_be
 */
public class DepositAccountExistException extends Exception {

    public DepositAccountExistException() {}
    
    public DepositAccountExistException(String msg) {
        super(msg);
    }
}
