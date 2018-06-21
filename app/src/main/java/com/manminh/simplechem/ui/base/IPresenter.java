package com.manminh.simplechem.ui.base;

public interface IPresenter<V extends IView> {
    void attachView(V view);

    void detachView();
}
