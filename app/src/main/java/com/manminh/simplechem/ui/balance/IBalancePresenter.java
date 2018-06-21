package com.manminh.simplechem.ui.balance;

import com.manminh.simplechem.ui.base.IPresenter;

public interface IBalancePresenter<V extends IBalanceView> extends IPresenter<V> {
    void balance(String equation);
}
