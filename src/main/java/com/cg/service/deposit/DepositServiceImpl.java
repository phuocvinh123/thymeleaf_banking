package com.cg.service.deposit;

import com.cg.model.Deposit;

import java.util.ArrayList;
import java.util.List;

public class DepositServiceImpl implements IDepositService {

    private final static List<Deposit> deposits = new ArrayList<>();

    private static Long id;

    @Override
    public List<Deposit> findAll() {
        return null;
    }

    @Override
    public Deposit findById(Long id) {
        return null;
    }

    @Override
    public void create(Deposit deposit) {
        deposit.setId(id++);
        deposit.setDeleted(false);
        deposits.add(deposit);
    }

    @Override
    public void update(Long id, Deposit deposit) {

    }

    @Override
    public void removeById(Long id) {

    }
}
