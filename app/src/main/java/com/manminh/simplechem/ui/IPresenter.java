package com.manminh.simplechem.ui;

public interface IPresenter<V extends IView> {
    void attachView(V view);

    void detachView();
}
