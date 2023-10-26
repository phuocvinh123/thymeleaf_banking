package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {

    void deposit(Deposit deposit);

    List<Customer> findAllWithoutId(Long id);
    void withdraw(Withdraw withdraw);
    List<Transfer> findByCustomer(Long customerId);
}
