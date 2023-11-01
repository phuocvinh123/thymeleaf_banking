package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "ten khong duoc de trong")
    private String fullName;
    @Column(nullable = false,unique = true)
    @NotBlank(message = "email khong duoc de trong")
    @Email(message = "email phai dung dinh dang")
    private String email;
    @NotBlank(message = "phone khong duoc de trong")

    private String phone;
    @NotBlank(message = "address khong duoc de trong")
    @Length(min = 6,message = "address it nhat phai 6 ki tu")
    private String address;
    @Column(columnDefinition = "decimal(20,0) ", nullable = false,updatable = false)
    private BigDecimal balance=BigDecimal.ZERO;
    private Boolean deleted =false;

}
