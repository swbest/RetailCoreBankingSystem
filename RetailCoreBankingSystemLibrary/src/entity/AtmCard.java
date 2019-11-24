/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author dtjldamien
 */
@Entity
public class AtmCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmCardId;
    @Column(nullable = false, length = 64, unique = true)
    private String cardNumber;
    @Column(nullable = false, length = 64)
    private String nameOnCard;
    @Column(nullable = false, length = 64)
    private Boolean enabled;
    @Column(nullable = false, length = 64)
    private String pin;
    
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    @OneToMany(mappedBy = "atmCard")
    private List<DepositAccount> depositAccounts;
    
    public AtmCard() {
        depositAccounts = new ArrayList<>();
    }

    public AtmCard(String cardNumber, String nameOnCard, String pin, Customer customer) {
        this();
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.pin = pin;
        this.customer = customer;
    }
    
    public Long getAtmCardId() {
        return atmCardId;
    }

    public void setAtmCardId(Long atmCardId) {
        this.atmCardId = atmCardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<DepositAccount> getDepositAccounts() {
        return depositAccounts;
    }

    public void setDepositAccounts(List<DepositAccount> depositAccounts) {
        this.depositAccounts = depositAccounts;
    }
    
    public void addDepositAccount(DepositAccount depositAccount)
    {
        if(!this.depositAccounts.contains(depositAccount))
        {
            this.depositAccounts.add(depositAccount);
        }
    }
    
    public void removeTransaction(DepositAccount depositAccount)
    {
        if(this.depositAccounts.contains(depositAccount))
        {
            this.depositAccounts.remove(depositAccount);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atmCardId != null ? atmCardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the atmCardId fields are not set
        if (!(object instanceof AtmCard)) {
            return false;
        }
        AtmCard other = (AtmCard) object;
        if ((this.atmCardId == null && other.atmCardId != null) || (this.atmCardId != null && !this.atmCardId.equals(other.atmCardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AtmCard[ id=" + atmCardId + " ]";
    }
}
