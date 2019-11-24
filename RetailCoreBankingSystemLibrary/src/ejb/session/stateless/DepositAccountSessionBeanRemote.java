/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccount;
import util.exception.CustomerNotFoundException;
import util.exception.DepositAccountNotFoundException;
import util.exception.DepositAccountExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author dtjldamien
 */
public interface DepositAccountSessionBeanRemote {

    public DepositAccount openNewDepositAccount(DepositAccount newDepositAccount, Long customerId) throws DepositAccountExistException, UnknownPersistenceException, CustomerNotFoundException;

    public DepositAccount retrieveDepositAccountByDepositAccountId(Long depositAccountId) throws DepositAccountNotFoundException;
}