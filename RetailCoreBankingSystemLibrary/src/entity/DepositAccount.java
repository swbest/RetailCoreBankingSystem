package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.DepositAccountType;

/**
 *
 * @author sw_be
 */
@Entity
public class DepositAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long depositAccountId;
    @Column(nullable = false, length = 64, unique = true)
    public String accountNumber;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public DepositAccountType accountType;
    @Column(nullable = false, precision = 18, scale = 4)
    public BigDecimal availableBalance;
    @Column(nullable = false, precision = 18, scale = 4)
    public BigDecimal holdBalance;
    @Column(nullable = false, precision = 18, scale = 4)
    public BigDecimal ledgerBalance;
    @Column(nullable = false)
    public Boolean enabled;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    @ManyToOne
    private AtmCard atmCard;
    @OneToMany(mappedBy = "depositAccount")
    private List<DepositAccountTransaction> transactions;
    
    public DepositAccount() {
        this.availableBalance = new BigDecimal("0.0000");
        this.holdBalance = new BigDecimal("0.0000");
        this.ledgerBalance = new BigDecimal("0.0000");
        
        transactions = new ArrayList<>();
    }

    public DepositAccount(String accountNumber, DepositAccountType accountType, BigDecimal availableBalance, BigDecimal holdBalance, BigDecimal ledgerBalance, Boolean enabled) {
        this();
        
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.availableBalance = availableBalance;
        this.holdBalance = holdBalance;
        this.ledgerBalance = ledgerBalance;
        this.enabled = enabled;
    }

    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public DepositAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(DepositAccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(BigDecimal holdBalance) {
        this.holdBalance = holdBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AtmCard getAtmCard() {
        return atmCard;
    }

    public void setAtmCard(AtmCard atmCard) {
        this.atmCard = atmCard;
    }

    public List<DepositAccountTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DepositAccountTransaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(DepositAccountTransaction transaction)
    {
        if(!this.transactions.contains(transaction))
        {
            this.transactions.add(transaction);
        }
    }
    
    public void removeTransaction(DepositAccountTransaction transaction)
    {
        if(this.transactions.contains(transaction))
        {
            this.transactions.remove(transaction);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountId != null ? depositAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountId fields are not set
        if (!(object instanceof DepositAccount)) {
            return false;
        }
        DepositAccount other = (DepositAccount) object;
        if ((this.depositAccountId == null && other.depositAccountId != null) || (this.depositAccountId != null && !this.depositAccountId.equals(other.depositAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccount[ id=" + depositAccountId + " ]";
    }
}
