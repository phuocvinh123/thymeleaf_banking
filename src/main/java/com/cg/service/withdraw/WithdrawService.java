package com.cg.service.withdraw;

import com.cg.model.Deposit;
import com.cg.model.Withdraw;

import java.util.ArrayList;
import java.util.List;

public class WithdrawService implements IWithdrawService{
    private final static List<Withdraw> withdraws = new ArrayList<>();

    private static Long id;

    @Override
    public List<Withdraw> findAll() {
        return null;
    }

    @Override
    public Withdraw findById(Long id) {
        return null;
    }

    @Override
    public void create(Withdraw withdraw) {
    withdraw.setId(id++);
    withdraw.setDeleted(false);
    withdraws.add(withdraw);
    }

    @Override
    public void update(Long id, Withdraw withdraw) {

    }

    @Override
    public void removeById(Long id) {

    }
}
