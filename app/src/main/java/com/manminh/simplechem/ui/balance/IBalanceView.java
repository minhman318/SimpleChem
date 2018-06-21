package com.manminh.simplechem.ui.balance;

import com.manminh.simplechem.ui.base.IView;

public interface IBalanceView extends IView {
    void showError(String error);

    void showResult(String result);

    void showInfo(String info);
}
