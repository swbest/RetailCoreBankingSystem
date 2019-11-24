package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.CustomerNotFoundException;
import util.exception.DepositAccountNotFoundException;
import util.exception.DepositAccountExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sw_be
 */
@Stateless
@Local(DepositAccountSessionBeanLocal.class)
@Remote(DepositAccountSessionBeanRemote.class)
public class DepositAccountSessionBean implements DepositAccountSessionBeanLocal, DepositAccountSessionBeanRemote {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public DepositAccount openNewDepositAccount(DepositAccount newDepositAccount, Long customerId) throws DepositAccountExistException, UnknownPersistenceException, CustomerNotFoundException {
        try {
            Customer customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);
            em.persist(newDepositAccount);
            newDepositAccount.setCustomer(customer);
            customer.addDepositAccount(newDepositAccount);
            em.flush();
            em.refresh(newDepositAccount);
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new DepositAccountExistException("Deposit Account with the same account number already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Customer record not found in the database!");
        }
        return newDepositAccount;
    }

    @Override
    public DepositAccount retrieveDepositAccountByDepositAccountId(Long depositAccountId) throws DepositAccountNotFoundException {
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);

        if (depositAccount != null) {
            return depositAccount;
        } else {
            throw new DepositAccountNotFoundException("Deposit Account ID " + depositAccountId + "does not exist!");
        }
    }
}