package com.manminh.simplechem.ui.search;

public interface ISearchPresenter {
    void search(String in, String out, int num);

    void searchMore(int num);

    void reset();
}
