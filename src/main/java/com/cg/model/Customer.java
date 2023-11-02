package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @Pattern(regexp = "^(?!.*\\d).*$", message = "Tên không được chứa chữ số")
    private String fullName;
    @Column(nullable = false,unique = true)
    @NotBlank(message = "email khong duoc de trong")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ")
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
