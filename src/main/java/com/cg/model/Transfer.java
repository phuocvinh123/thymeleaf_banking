package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transfers")
public class Transfer implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;
    @Column(columnDefinition = "decimal(20,2)")
    private BigDecimal transferAmount;
    private Long fees;
    @Column(columnDefinition = "decimal(20,0)")
    private BigDecimal feesAmount;
    @Column(columnDefinition = "decimal(20,0)")
    private BigDecimal transactionAmount;
    private LocalDateTime dateTransfer;
    private Boolean deleted = false;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Transfer transfer = (Transfer) o;
        BigDecimal transferAmount = transfer.getTransferAmount();
        Customer customer = transfer.getSender();
        if (transferAmount == null) {
            errors.rejectValue("transferAmount", "transfer.transferAmount.null");
            return;
        }
        if (transferAmount.compareTo(BigDecimal.valueOf(1000000000)) > 0) {
            errors.rejectValue("transferAmount", "transfer.transferAmount.max");
            return;
        }
        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("transferAmount", "transfer.transferAmount.min");
            return;
        }
        if (transferAmount.multiply(BigDecimal.valueOf(1.1)).compareTo(customer.getBalance()) > 0) {
            errors.rejectValue("transactionAmount", "transfer.transactionAmount.biggerBalance");
        }
    }
}
