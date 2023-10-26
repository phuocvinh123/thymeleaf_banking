package com.cg.service.transfer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferServiceImpl implements ITransferService {

    private final static List<Transfer> transfers = new ArrayList<>();

    @Override
    public List<Transfer> findAll() {
        return transfers;
    }

    @Override
    public Transfer findById(Long id) {
        return null;
    }

    @Override
    public void create(Transfer transfer) {
        BigDecimal transferAmount = transfer.getTransferAmount();

        Customer sender = transfer.getSender();
        Customer recipient = transfer.getRecipient();
        transfer.setSender(sender);
        transfer.setTransferAmount(transferAmount);

        transfer.setRecipient(recipient);
        transfer.setDeleted(false);
        transfers.add(transfer);
    }

    @Override
    public void update(Long id, Transfer transfer) {

    }

    @Override
    public void removeById(Long id) {

    }
}
