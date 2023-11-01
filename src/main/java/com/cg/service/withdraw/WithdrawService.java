package com.cg.service.withdraw;


import com.cg.model.Customer;
import com.cg.model.Withdraw;
import com.cg.repository.CustomerRepository;
import com.cg.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WithdrawService implements IWithdrawService {
    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Withdraw findById(Long id) {
        return withdrawRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Withdraw withdraw) {
        withdraw.setDeleted(false);
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.parse(currentDate.format(formatter), formatter);
        withdraw.setDateWithdraw(formattedDate);
        customerRepository.reduceBalance(withdraw.getTransactionAmount(), withdraw.getCustomer().getId());
        withdrawRepository.save(withdraw);
    }

    @Override
    public void delete(Withdraw withdraw) {
        withdrawRepository.delete(withdraw);
    }

    @Override
    public void deleteById(Long aLong) {
        withdrawRepository.deleteById(aLong);
    }

    @Override
    public boolean isValidWithdrawal(Withdraw withdraw) {
        Customer customer = withdraw.getCustomer();
        BigDecimal withdrawalAmount = withdraw.getTransactionAmount();

        if (withdrawalAmount == null || withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0 || withdrawalAmount.compareTo(customer.getBalance()) > 0) {
            return false;
        }
        return true;
    }
}
