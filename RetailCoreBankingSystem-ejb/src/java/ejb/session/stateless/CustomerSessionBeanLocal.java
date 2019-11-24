/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author dtjldamien
 */
public interface CustomerSessionBeanLocal {
    
    public Customer createNewCustomer(Customer newCustomer) throws CustomerUsernameExistException, UnknownPersistenceException;

    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException;
}
