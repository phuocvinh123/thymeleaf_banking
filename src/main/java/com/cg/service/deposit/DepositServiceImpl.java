package com.cg.service.deposit;

import com.cg.model.Deposit;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class DepositServiceImpl implements IDepositService {

    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Deposit findById(Long id) {
        return depositRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Deposit deposit) {
        deposit.setDeleted(false);
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.parse(currentDate.format(formatter), formatter);
        deposit.setDateDeposit(formattedDate);
        customerRepository.incrementBalance(deposit.getTransactionAmount(), deposit.getCustomer().getId());
        depositRepository.save(deposit);
    }

    @Override
    public void delete(Deposit deposit) {
        depositRepository.delete(deposit);
    }

    @Override
    public void deleteById(Long aLong) {
        depositRepository.deleteById(aLong);
    }
}
