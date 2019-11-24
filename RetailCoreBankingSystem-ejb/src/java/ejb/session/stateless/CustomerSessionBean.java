package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sw_be
 */
@Stateless
@Local(CustomerSessionBeanLocal.class)
@Remote(CustomerSessionBeanRemote.class)
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    public CustomerSessionBean() {
    }

    @Override
    public Customer createNewCustomer(Customer newCustomer) throws CustomerUsernameExistException, UnknownPersistenceException {
        try {
            em.persist(newCustomer);
            em.flush();
            return newCustomer;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new CustomerUsernameExistException("A customer with the same identification number exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException("Customer ID " + customerId + " does not exist");
        }
    }
}
