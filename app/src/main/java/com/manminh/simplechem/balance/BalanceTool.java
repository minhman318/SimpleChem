package com.manminh.simplechem.balance;

import com.manminh.simplechem.balance.engine.BalanceEngine;
import com.manminh.simplechem.model.Equation;

public class BalanceTool {
    public static boolean balance(BalanceEngine engine, Equation before) {
        return engine.balance(before);
    }
}
