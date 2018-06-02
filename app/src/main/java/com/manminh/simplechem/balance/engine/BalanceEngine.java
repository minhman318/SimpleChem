package com.manminh.simplechem.balance.engine;

import com.manminh.simplechem.balance.model.Equation;

public interface BalanceEngine {
    Equation balance(Equation before);
}
