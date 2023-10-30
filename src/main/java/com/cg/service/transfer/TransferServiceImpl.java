package com.cg.service.transfer;


import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransferServiceImpl implements ITransferService {
@Autowired
private TransferRepository transferRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer findById(Long id) {
        return transferRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Transfer transfer) {
        transfer.setDeleted(false);
         transferRepository.save(transfer);
    }

    @Override
    public void delete(Transfer transfer) {
        transferRepository.delete(transfer);
    }

    @Override
    public void deleteById(Long aLong) {
        transferRepository.deleteById(aLong);
    }
    @Override
    public boolean isValidTransfer(Transfer transfer) {
        Customer sender = transfer.getSender();
        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal transferFee = transferAmount.multiply(BigDecimal.valueOf(0.1));
        BigDecimal totalAmount = transferAmount.add(transferFee);
        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0 || transferAmount.compareTo(sender.getBalance()) > 0) {
            return false;
        }
        if (totalAmount.compareTo(sender.getBalance()) > 0) {
            return false;
        }
        return true;
    }
}
