package com.cg.model;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "withdraws")
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name= "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @Column( columnDefinition = "decimal(20,0)")
    private BigDecimal transactionAmount;
    private Boolean deleted;

    public Withdraw() {
    }

    public Withdraw(Long id, Customer customer, BigDecimal transactionAmount, Boolean deleted) {
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
