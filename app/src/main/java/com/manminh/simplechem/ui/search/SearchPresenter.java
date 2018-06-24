package com.manminh.simplechem.ui.search;

import com.manminh.simplechem.search.SearchResult;
import com.manminh.simplechem.search.SearchTool;
import com.manminh.simplechem.search.engine.PTHHSearchEngine;
import com.manminh.simplechem.search.engine.SearchEngine;
import com.manminh.simplechem.ui.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter<V extends ISearchView> extends BasePresenter<V> implements
        ISearchPresenter, SearchTool.OnSearchResult {

    private String mBeforeStr;
    private String mAfterStr;
    private SearchTool mTool;
    private SearchEngine mEngine;
    private List<SearchResult> mResults = null;

    public SearchPresenter() {
        mTool = new SearchTool();
        mEngine = new PTHHSearchEngine();
    }

    @Override
    public void onResult(List<SearchResult> searchResults) {
        V view = getView();
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
    }

    @Override
    public void onError() {
        V view = getView();
        view.hideLoading();
        view.showList();
        view.showInfo("Không thể tìm kiếm. Kiểm tra Internet!");
    }

    @Override
    public void search(String in, String out, int num) {
        mBeforeStr = in;
        mAfterStr = out;
        getView().showLoading();
        getView().hideList();
        mTool.search(mEngine, in, out, this);
    }

    @Override
    public void more(String in, String out, int num) {
        return;
    }

    public void onSelected(int pos) {
        if (mResults != null) {
            SearchResult result = mResults.get(pos);
            getView().seeDetails(result.getEquation(), result.getDetails());
        }
    }
}
