/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.AtmCardLinkDepositAccountException;
import util.exception.AtmCardNotFoundException;
import util.exception.AtmCardNumberExistException;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteAtmCardException;
import util.exception.DepositAccountNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author dtjldamien
 */
@Stateless
@Local(AtmCardSessionBeanLocal.class)
@Remote(AtmCardSessionBeanRemote.class)
public class AtmCardSessionBean implements AtmCardSessionBeanLocal, AtmCardSessionBeanRemote {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    @EJB
    private DepositAccountSessionBeanLocal depositAccountSessionBeanLocal;
    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public AtmCard issueNewAtmCard(AtmCard atmCard, Long customerId, List<Long> depositAccountIds) throws AtmCardNumberExistException, UnknownPersistenceException, AtmCardLinkDepositAccountException, CustomerNotFoundException, DepositAccountNotFoundException, Exception {
        try {
            Customer customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);

            em.persist(atmCard);

            atmCard.setCustomer(customer);
            customer.setAtmCard(atmCard);

            for (Long depositAccountId : depositAccountIds) {
                DepositAccount depositAccount = depositAccountSessionBeanLocal.retrieveDepositAccountByDepositAccountId(depositAccountId);

                if (depositAccount.getCustomer().equals(customer)) {
                    depositAccount.setAtmCard(atmCard);
                    atmCard.getDepositAccounts().add(depositAccount);
                } else {
                    throw new AtmCardLinkDepositAccountException("ATM card holder is different from deposit account holder");
                }
            }

            em.flush();
            em.refresh(atmCard);

            return atmCard;
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Unable to create new ATM card as the customer record does not exist");
        } catch (DepositAccountNotFoundException ex) {
            throw new DepositAccountNotFoundException(
                    "Unable to create new ATM card as the deposit account record does not exist");
        } catch (AtmCardLinkDepositAccountException ex) {
            throw new AtmCardLinkDepositAccountException(ex.getMessage());
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getClass()
                    .getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                throw new AtmCardNumberExistException("Atm card with same card number already exist");
            } else {
                throw new Exception("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    @Override
    public AtmCard retrieveAtmCardByAtmCardId(Long atmCardId) throws AtmCardNotFoundException {
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);

        if (atmCard != null) {
            return atmCard;
        } else {
            throw new AtmCardNotFoundException("Atm Card ID " + atmCard + " does not exist");
        }
    }

    @Override
    public void changePin(Long atmCardId, String currPin, String newPin) throws AtmCardNotFoundException {
        AtmCard atmCard = retrieveAtmCardByAtmCardId(atmCardId);
        if (atmCard.getPin().equals(currPin)) {
            atmCard.setPin(newPin);
        } else {
            throw new AtmCardNotFoundException("Card number of AtmCard record to be updated does not match the existing record");
        }
    }

    @Override
    public void deleteAtmCard(Long atmCardId) throws AtmCardNotFoundException, DeleteAtmCardException {
        AtmCard atmCardToRemove = retrieveAtmCardByAtmCardId(atmCardId);
        atmCardToRemove.getCustomer().setAtmCard(null);
        for (DepositAccount depositAccount : atmCardToRemove.getDepositAccounts()) {
            depositAccount.setAtmCard(null);
        }
        atmCardToRemove.getDepositAccounts().clear();
        em.remove(atmCardToRemove);
    }

    @Override
    public List<DepositAccount> retrieveDepositAccountsByAtmCardId(Long atmCardId) throws AtmCardNotFoundException {
        AtmCard atmCard = retrieveAtmCardByAtmCardId(atmCardId);
        List<DepositAccount> depositAccounts = atmCard.getDepositAccounts();

        for (DepositAccount depositAccount : depositAccounts) {
            em.detach(depositAccount);
            depositAccount.setCustomer(null);
            depositAccount.setAtmCard(null);
        }

        return depositAccounts;
    }

    @Override
    public void linkDepositAccountToAtmCard(Long atmCardId, Long depositAccountId) throws AtmCardLinkDepositAccountException {
        try {
            AtmCard atmCard = retrieveAtmCardByAtmCardId(atmCardId);
            DepositAccount depositAccount = depositAccountSessionBeanLocal.retrieveDepositAccountByDepositAccountId(depositAccountId);

            if (atmCard.getCustomer().equals(depositAccount.getCustomer())) {
                if (!atmCard.getDepositAccounts().contains(depositAccount)) {
                    atmCard.getDepositAccounts().add(depositAccount);
                    depositAccount.setAtmCard(atmCard);
                } else {
                    throw new AtmCardLinkDepositAccountException("The deposit account is already linked to the ATM card");
                }
            } else {
                throw new AtmCardLinkDepositAccountException("ATM card holder is different from deposit account holder");
            }
        } catch (AtmCardNotFoundException | DepositAccountNotFoundException ex) {
            throw new AtmCardLinkDepositAccountException(ex.getMessage());
        }
    }
}
