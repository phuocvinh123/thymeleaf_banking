package com.cg.service.withdraw;

import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.service.IGeneralService;

public interface IWithdrawService extends IGeneralService<Withdraw, Long> {
    boolean isValidWithdrawal(Withdraw withdraw);
}
