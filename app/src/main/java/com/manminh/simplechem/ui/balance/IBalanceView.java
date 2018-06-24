package com.manminh.simplechem.ui.balance;

import android.text.Spanned;

import com.manminh.simplechem.ui.IView;

public interface IBalanceView extends IView {
    void showError(String error);

    void showInfo(String info);

    void showResult(Spanned result);

    void onShowResult();
}
