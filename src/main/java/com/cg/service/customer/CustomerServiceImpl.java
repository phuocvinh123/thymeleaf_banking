package com.cg.service.customer;

import com.cg.model.Customer;

import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Customer customer) {
        if(customer.getBalance()==null){
            customer.setBalance(BigDecimal.ZERO);
        }
        customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public void deleteById(Long aLong) {
        customerRepository.deleteById(aLong);
    }


    @Override
    public void deposit(Deposit deposit) {
        Customer customer = deposit.getCustomer();
        BigDecimal transactionAmount = deposit.getTransactionAmount();

        BigDecimal currentBalance = customer.getBalance();
        BigDecimal newBalance = currentBalance.add(transactionAmount);
        customer.setBalance(newBalance);

        save(customer);
    }

    @Override
    public void withdraw(Withdraw withdraw) {
        Customer customer = withdraw.getCustomer();
        BigDecimal withdrawalAmount = withdraw.getTransactionAmount();

        BigDecimal updatedBalance = customer.getBalance().subtract(withdrawalAmount);
        customer.setBalance(updatedBalance);

        save(customer);

    }

    @Override
    public void transfer(Transfer transfer) {
        Customer sender = transfer.getSender();
        Customer recipient = transfer.getRecipient();
        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal transferFee = transferAmount.multiply(BigDecimal.valueOf(0.1));
        BigDecimal totalAmount = transferAmount.add(transferFee);

        BigDecimal updatedSenderBalance = sender.getBalance().subtract(totalAmount);
        BigDecimal updatedRecipientBalance = recipient.getBalance().add(transferAmount);

        sender.setBalance(updatedSenderBalance);
        recipient.setBalance(updatedRecipientBalance);

        save(sender);
        save(recipient);
        transfer.setFeesAmount(transferFee);
        transfer.setTransactionAmount(totalAmount);
        transfer.setDateTransfer(LocalDateTime.now());
    }

    @Override
    public List<Customer> findAllByIdNot(Long senderId) {
        return customerRepository.findAllByIdNot(senderId);
    }
}
