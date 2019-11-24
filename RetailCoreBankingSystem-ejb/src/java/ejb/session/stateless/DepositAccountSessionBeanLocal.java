package ejb.session.stateless;

import entity.DepositAccount;
import util.exception.CustomerNotFoundException;
import util.exception.DepositAccountNotFoundException;
import util.exception.DepositAccountExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sw_be
 */
public interface DepositAccountSessionBeanLocal {

    public DepositAccount openNewDepositAccount(DepositAccount newDepositAccount, Long customerId) throws DepositAccountExistException, UnknownPersistenceException, CustomerNotFoundException;

    public DepositAccount retrieveDepositAccountByDepositAccountId(Long depositAccountId) throws DepositAccountNotFoundException;    
}