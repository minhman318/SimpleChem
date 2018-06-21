package com.manminh.simplechem.balance.engine;

import com.manminh.simplechem.model.Equation;


public interface BalanceEngine {

    /**
     * Balance an equation object, may modify the equation if balance successfully
     *
     * @param equation
     * @throws FailedBalanceException if cannot balance
     *                                use FailedBalanceException.getExceptionCode() for more details
     */
    void balance(Equation equation) throws FailedBalanceException;
}
