package com.cg.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name= "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;
    @ManyToOne
    @JoinColumn(name= "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;
    @Column(precision = 10, scale = 2, columnDefinition = "decimal(10,2)")
    private BigDecimal transferAmount;
    private Long fees;
    @Column( columnDefinition = "decimal(20,0)")
    private BigDecimal feesAmount;
    @Column( columnDefinition = "decimal(20,0)")
    private BigDecimal transactionAmount;
    private LocalDateTime dateTransfer;
    private Boolean deleted;

    public Transfer() {
    }


    public Transfer(Long id, Customer sender, Customer recipient, BigDecimal transferAmount, Long fees, BigDecimal feesAmount, BigDecimal transactionAmount, LocalDateTime dateTransfer, Boolean deleted) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.transferAmount = transferAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.transactionAmount = transactionAmount;
        this.dateTransfer = dateTransfer;
        this.deleted = deleted;
    }

    public LocalDateTime getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(LocalDateTime dateTransfer) {
        this.dateTransfer = dateTransfer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getFees() {
        return fees;
    }

    public void setFees(Long fees) {
        this.fees = fees;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(BigDecimal feesAmount) {
        this.feesAmount = feesAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

//    public String createDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//        return dateTransfer.format(formatter);
//    }
}
