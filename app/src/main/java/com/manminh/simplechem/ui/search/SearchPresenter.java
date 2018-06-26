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

    private static final String CANNOT_SEARCH_TXT = "Không thể tìm kiếm. Kiểm tra kết nối.";
    private static final String EMPTY_RESULT_TXT = "Không tìm thấy kết quả.";

    private SearchTool mTool;
    private SearchEngine mEngine;
    private List<SearchResult> mResults = null;
    private int mCurrentPage = 1;

    public SearchPresenter() {
        mTool = new SearchTool();
        mEngine = new PTHHSearchEngine();
    }

    @Override
    public synchronized void onResult(List<SearchResult> searchResults, int lastPage) {
        V view = getView();
        if (view == null) {
            return;
        }
        mCurrentPage = lastPage;
        if (mResults == null) {
            view.hideLoading();
            view.showList();
            if (searchResults.size() < 1) {
                view.showInfo(EMPTY_RESULT_TXT);
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
        view.showInfo(CANNOT_SEARCH_TXT);
    }

    @Override
    public void search(String breforeStr, String afterStr, int numItem) {
        reset();
        getView().showLoading();
        getView().hideList();
        mTool.search(mEngine, breforeStr, afterStr, numItem, mCurrentPage, this);
    }

    @Override
    public void searchMore(int numItem) {
        getView().showLoading();
        mCurrentPage++;
        mTool.searchMore(numItem, mCurrentPage);
    }

    public void onSelected(int position) {
        if (mResults != null) {
            SearchResult result = mResults.get(position);
            getView().seeDetails(result.getEquation(), result.getDetails());
        }
    }

    @Override
    public void reset() {
        mCurrentPage = 1;
        mResults = null;
    }
}
