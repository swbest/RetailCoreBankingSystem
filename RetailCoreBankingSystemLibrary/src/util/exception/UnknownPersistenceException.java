package util.exception;

/**
 *
 * @author sw_be
 */
public class UnknownPersistenceException extends Exception {
    
    public UnknownPersistenceException() { }
    

    public UnknownPersistenceException(String msg){
        super(msg);
    }
}
