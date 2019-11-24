package ejb.session.stateless;

import entity.Customer;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sw_be
 */
public interface CustomerSessionBeanRemote {
    
    public Customer createNewCustomer(Customer newCustomer) throws CustomerUsernameExistException, UnknownPersistenceException;

    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException;
}
