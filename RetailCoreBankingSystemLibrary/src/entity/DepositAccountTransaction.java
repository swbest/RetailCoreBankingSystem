/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.TransactionStatus;
import util.enumeration.TransactionType;

/**
 *
 * @author dtjldamien
 */
@Entity
public class DepositAccountTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long depositAccountTransactionId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date transactionDateTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransactionType type;
    @Column(nullable = false, length = 64)
    public String code;
    @Column(nullable = false, length = 64)
    public String reference;
    @Column(nullable = false, precision = 11, scale = 2)
    public BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransactionStatus status;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private DepositAccountTransaction sourceTransaction;
    @OneToOne
    private DepositAccountTransaction destinationTransaction;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private DepositAccount depositAccount;
    
    public DepositAccountTransaction() {}

    public DepositAccountTransaction(Date transactionDateTime, TransactionType type, String code, String reference, BigDecimal amount, TransactionStatus status) {
        this();
        this.transactionDateTime = transactionDateTime;
        this.type = type;
        this.code = code;
        this.reference = reference;
        this.amount = amount;
        this.status = status;
    }

    public Long getDepositAccountTransactionId() {
        return depositAccountTransactionId;
    }

    public void setDepositAccountTransactionId(Long depositAccountTransactionId) {
        this.depositAccountTransactionId = depositAccountTransactionId;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public DepositAccountTransaction getSourceTransaction() {
        return sourceTransaction;
    }

    public void setSourceTransaction(DepositAccountTransaction sourceTransaction) {
        this.sourceTransaction = sourceTransaction;
    }

    public DepositAccountTransaction getDestinationTransaction() {
        return destinationTransaction;
    }

    public void setDestinationTransaction(DepositAccountTransaction destinationTransaction) {
        this.destinationTransaction = destinationTransaction;
    }

    public DepositAccount getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(DepositAccount depositAccount) {
        this.depositAccount = depositAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountTransactionId != null ? depositAccountTransactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountTransactionId fields are not set
        if (!(object instanceof DepositAccountTransaction)) {
            return false;
        }
        DepositAccountTransaction other = (DepositAccountTransaction) object;
        if ((this.depositAccountTransactionId == null && other.depositAccountTransactionId != null) || (this.depositAccountTransactionId != null && !this.depositAccountTransactionId.equals(other.depositAccountTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccountTransaction[ id=" + depositAccountTransactionId + " ]";
    }
}
