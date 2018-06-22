package com.manminh.simplechem.balance.engine;

import com.manminh.simplechem.balance.exception.FailedBalanceException;
import com.manminh.simplechem.model.Equation;

/**
 * Interface of balance algorithm implementation
 */
public interface IBalanceEngine {

    /**
     * Balance an equation object, may modify the equation if balance successfully
     *
     * @param equation
     * @throws FailedBalanceException if cannot balance
     *                                use FailedBalanceException.getExceptionCode() for more details
     */
    void balance(Equation equation) throws FailedBalanceException;
}
