package util.exception;

/**
 *
 * @author sw_be
 */
public class CustomerNotFoundException extends Exception 
{
    public CustomerNotFoundException() 
    {
    }

    
    
    public CustomerNotFoundException(String msg) 
    {
        super(msg);
    }
}