package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    void deposit(Deposit deposit);
    void withdraw(Withdraw withdraw);
    void transfer(Transfer transfer);
    List<Customer> findAllByIdNot(Long senderId);
}
