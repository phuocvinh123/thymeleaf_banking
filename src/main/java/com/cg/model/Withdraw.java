package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "withdraws")
public class Withdraw implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @Column(columnDefinition = "decimal(20,0)")
    private BigDecimal transactionAmount;
    private LocalDateTime dateWithdraw;
    private Boolean deleted = false;

    public Withdraw(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Withdraw withdraw = (Withdraw) o;
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        Customer customer = withdraw.getCustomer();
        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "withdraw.transactionAmount.null", "so tien khong duoc de trong");
            return;
        }
        if (transactionAmount.compareTo(BigDecimal.valueOf(1000000000)) > 0) {
            errors.rejectValue("transactionAmount", "withdraw.transactionAmount.1000000000", "so tien rut khong qua 1.000.000.000");
            return;
        }
        if (transactionAmount.compareTo(customer.getBalance()) > 0) {
            errors.rejectValue("transactionAmount","withdraw.transactionAmount.biggerBalance");
        }
    }
}
