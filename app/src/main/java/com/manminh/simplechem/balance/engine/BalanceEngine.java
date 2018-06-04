package com.manminh.simplechem.balance.engine;

import com.manminh.simplechem.exception.FailedBalanceException;
import com.manminh.simplechem.model.Equation;

public interface BalanceEngine {
    void balance(Equation equation) throws FailedBalanceException;
}
