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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


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
    public void incrementBalance(BigDecimal balance, Long id) {
        customerRepository.incrementBalance(balance, id);
    }

    @Override
    public void reduceBalance(BigDecimal balance, Long id) {
        customerRepository.reduceBalance(balance, id);
    }

    @Override
    public List<Customer> findAll(boolean deleted) {
        List<Customer> customer = findAll();
        return customer.stream().filter(cus -> !cus.getDeleted()).collect(Collectors.toList());
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Customer customer) {
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
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.parse(currentDate.format(formatter), formatter);
        deposit.setDateDeposit(formattedDate);
        save(customer);
    }

    @Override
    public void withdraw(Withdraw withdraw) {
        Customer customer = withdraw.getCustomer();
        BigDecimal withdrawalAmount = withdraw.getTransactionAmount();

        BigDecimal updatedBalance = customer.getBalance().subtract(withdrawalAmount);
        customer.setBalance(updatedBalance);
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.parse(currentDate.format(formatter), formatter);
        withdraw.setDateWithdraw(formattedDate);
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
        transfer.setFees(10L);
        transfer.setFeesAmount(transferFee);
        transfer.setTransactionAmount(totalAmount);
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.parse(currentDate.format(formatter), formatter);
        transfer.setDateTransfer(formattedDate);

    }

    @Override
    public List<Customer> findAllByIdNot(Long senderId) {
        return customerRepository.findAllByIdNot(senderId);
    }
}
