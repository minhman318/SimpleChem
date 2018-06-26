package com.manminh.simplechem.ui.search;

import com.manminh.simplechem.search.EquationDetail;
import com.manminh.simplechem.ui.IView;

import java.util.ArrayList;
import java.util.List;

public interface ISearchView extends IView {
    void showLoading();

    void hideLoading();

    void showList();

    void hideList();

    void setUpItems(List<String> equations);

    void addMoreItems(List<String> equations);

    void showInfo(String info);

    void seeDetails(String equation, ArrayList<EquationDetail> info);

    void toMoreButton();

    void toSearchButton();
}
