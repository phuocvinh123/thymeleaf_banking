package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deposits")
public class Deposit implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @Column(columnDefinition = "decimal(20,0)")
    private BigDecimal transactionAmount;
    private LocalDateTime dateDeposit;
    private Boolean deleted = false;

    public Deposit(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Deposit deposit= (Deposit) o;
        BigDecimal transactionAmount = deposit.transactionAmount;
        if(transactionAmount==null){
            errors.rejectValue("transactionAmount","deposit.transactionAmount.null");
            return;
        }
        if(transactionAmount.compareTo(BigDecimal.valueOf(10000))<0){
            errors.rejectValue("transactionAmount","deposit.transactionAmount.min");
            return;
        }if (transactionAmount.compareTo(BigDecimal.valueOf(1000000000))> 0) {
            errors.rejectValue("transactionAmount", "deposit.transactionAmount.1000000000");

        }
    }
}
