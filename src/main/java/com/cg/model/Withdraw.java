package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDateTime dateWithdraw;
    private Boolean deleted =false;

}
