package com.cg.model;

import java.math.BigDecimal;

public class Deposit {
    private Long id;
    private Customer customer;
    private BigDecimal transactionAmount;
    private Boolean deleted;

    public Deposit() {
    }

    public Deposit(Long id, Customer customer, BigDecimal transactionAmount, Boolean deleted) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
}
