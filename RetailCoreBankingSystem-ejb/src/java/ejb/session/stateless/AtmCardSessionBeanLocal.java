/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.DepositAccount;
import java.util.List;
import util.exception.AtmCardLinkDepositAccountException;
import util.exception.AtmCardNotFoundException;
import util.exception.AtmCardNumberExistException;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteAtmCardException;
import util.exception.DepositAccountNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAtmCardException;

/**
 *
 * @author dtjldamien
 */
public interface AtmCardSessionBeanLocal {

    
    public void deleteAtmCard(Long atmCardId) throws AtmCardNotFoundException, DeleteAtmCardException;

    public void changePin(Long atmCardId, String currPin, String newPin) throws AtmCardNotFoundException;

    public AtmCard retrieveAtmCardByAtmCardId(Long atmCardId) throws AtmCardNotFoundException;

    public List<DepositAccount> retrieveDepositAccountsByAtmCardId(Long atmCardId) throws AtmCardNotFoundException;

    public void linkDepositAccountToAtmCard(Long atmCardId, Long depositAccountId) throws AtmCardLinkDepositAccountException;

    public AtmCard issueNewAtmCard(AtmCard atmCard, Long customerId, List<Long> depositAccountIds) throws AtmCardNumberExistException, UnknownPersistenceException, AtmCardLinkDepositAccountException, CustomerNotFoundException, DepositAccountNotFoundException, Exception;
    
}
