package util.exception;

/**
 *
 * @author sw_be
 */
public class CustomerUsernameExistException extends Exception {
    
    public CustomerUsernameExistException() {}
    
    public CustomerUsernameExistException(String msg) {
        super(msg);
    }
}
