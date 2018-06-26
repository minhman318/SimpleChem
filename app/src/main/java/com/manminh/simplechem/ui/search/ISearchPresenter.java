package com.manminh.simplechem.ui.search;

public interface ISearchPresenter {
    void search(String beforeStr, String afterStr, int numItem);

    void searchMore(int numItem);

    void reset();
}
