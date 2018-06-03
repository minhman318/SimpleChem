package com.manminh.simplechem.balance.engine;

import com.manminh.simplechem.model.Equation;

public interface BalanceEngine {
    boolean balance(Equation equation);
}
