package com.manminh.simplechem.balance;

import com.manminh.simplechem.balance.engine.BalanceEngine;
import com.manminh.simplechem.model.Equation;

public class BalanceTool {
    public static void balance(BalanceEngine engine, Equation before) {
        engine.balance(before);
    }
}
