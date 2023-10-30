package com.cg.service.deposit;

import com.cg.model.Deposit;
import com.cg.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class DepositServiceImpl implements IDepositService {

    @Autowired
    private DepositRepository depositRepository;

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
