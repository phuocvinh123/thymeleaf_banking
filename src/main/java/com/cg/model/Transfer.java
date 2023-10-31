package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(columnDefinition = "decimal(20,2)")
    private BigDecimal transferAmount;
    private Long fees;
    @Column( columnDefinition = "decimal(20,0)")
    private BigDecimal feesAmount;
    @Column( columnDefinition = "decimal(20,0)")
    private BigDecimal transactionAmount;
    private LocalDateTime dateTransfer;
    private Boolean deleted =false;

}
