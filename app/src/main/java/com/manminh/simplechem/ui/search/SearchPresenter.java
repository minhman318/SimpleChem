package com.manminh.simplechem.ui.search;

import android.util.Log;

import com.manminh.simplechem.search.SearchResult;
import com.manminh.simplechem.search.SearchTool;
import com.manminh.simplechem.search.engine.PTHHSearchEngine;
import com.manminh.simplechem.search.engine.SearchEngine;
import com.manminh.simplechem.ui.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter<V extends ISearchView> extends BasePresenter<V> implements
        ISearchPresenter, SearchTool.OnSearchResult {

    private SearchTool mTool;
    private SearchEngine mEngine;
    private List<SearchResult> mResults = null;
    private int mPage = 1;

    public SearchPresenter() {
        mTool = new SearchTool();
        mEngine = new PTHHSearchEngine();
    }

    @Override
    public synchronized void onResult(List<SearchResult> searchResults, int page) {
        V view = getView();
        if (view == null) {
            return;
        }
        mPage = page;
        if (mResults == null) {
            view.hideLoading();
            view.showList();
            if (searchResults.size() < 1) {
                view.showInfo("Không tìm thấy kết quả");
                return;
            }
            mResults = searchResults;
            List<String> equations = new ArrayList<>();
            for (SearchResult e : searchResults) {
                equations.add(e.getEquation());
            }
            view.setUpItems(equations);
        } else {
            getView().hideLoading();
            mResults.addAll(searchResults);
            List<String> equations = new ArrayList<>();
            for (SearchResult e : searchResults) {
                equations.add(e.getEquation());
            }
            view.addMoreItems(equations);
        }
        getView().toMoreButton();
    }

    @Override
    public void onError() {
        V view = getView();
        if (view == null) {
            return;
        }
        view.hideLoading();
        view.showList();
        view.showInfo("Không thể tìm kiếm. Kiểm tra Internet!");
    }

    @Override
    public void search(String in, String out, int num) {
        reset();
        mResults = null;
        getView().showLoading();
        getView().hideList();
        mTool.search(mEngine, in, out, num, mPage, this);
    }

    @Override
    public void searchMore(int num) {
        getView().showLoading();
        mPage++;
        mTool.searchMore(num, mPage);
    }

    public void onSelected(int pos) {
        if (mResults != null) {
            SearchResult result = mResults.get(pos);
            getView().seeDetails(result.getEquation(), result.getDetails());
        }
    }

    @Override
    public void reset() {
        mPage = 1;
    }
}
