package com.manminh.simplechem.search;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.manminh.simplechem.search.engine.SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Use an thread pool to search multi thread, multi page
 */
public class SearchTool implements Runnable {
    private static final int ERROR = 0;
    private static final int SUCESSFUL = 1;

    public interface OnSearchResult {
        void onResult(List<SearchResult> searchResults, int lastPage);

        void onError();
    }

    private SearchEngine mEngine;
    private String mBeforeStr;
    private String mAfterStr;
    private Integer mCurrentPage;
    private boolean mStopAll = false;

    private OnSearchResult mListener;

    /**
     * Main search method
     * Result can be get from onResult
     *
     * @param engine    is SearchEngine
     * @param beforeStr is before chemicals string
     * @param afterStr  is after chemicals string
     * @param numItem   is number result items want to return
     * @param page      is which page to start
     * @param listener  is who want to receive result
     */
    public void search(SearchEngine engine
            , String beforeStr
            , String afterStr
            , int numItem
            , int page
            , OnSearchResult listener) {

        mStopAll = false;
        mCurrentPage = page;
        mEngine = engine;
        mBeforeStr = beforeStr;
        mAfterStr = afterStr;
        mListener = listener;

        int loop;
        if (numItem % engine.getNumberItemsPerPage() == 0) {
            loop = numItem / engine.getNumberItemsPerPage();
        } else {
            loop = numItem / engine.getNumberItemsPerPage() + 1;
        }

        MyThreadPoolExecutor poolExecutor = MyThreadPoolExecutor.getInstance();
        for (int i = 0; i < loop; i++) {
            poolExecutor.execute(this);
        }
    }

    /**
     * search more result
     *
     * @param numItem is number of items
     * @param page    is the last page
     */
    public void searchMore(int numItem, int page) {
        mStopAll = false;
        mCurrentPage = page;

        int loop;
        if (numItem % mEngine.getNumberItemsPerPage() == 0) {
            loop = numItem / mEngine.getNumberItemsPerPage();
        } else {
            loop = numItem / mEngine.getNumberItemsPerPage() + 1;
        }

        MyThreadPoolExecutor poolExecutor = MyThreadPoolExecutor.getInstance();
        for (int i = 0; i < loop; i++) {
            poolExecutor.execute(this);
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCESSFUL:
                    ArrayList<SearchResult> searchResults = (ArrayList<SearchResult>) msg.obj;
                    int lastPage = msg.arg1;
                    mListener.onResult(searchResults, lastPage);
                    break;
                case ERROR:
                    mListener.onError();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void run() {
        Message msg = mHandler.obtainMessage();
        try {
            if (!mStopAll) {
                ArrayList<SearchResult> searchResult;
                int page;
                synchronized (mCurrentPage) {
                    page = mCurrentPage;
                    mCurrentPage++;
                }
                searchResult = mEngine.search(mBeforeStr, mAfterStr, page);
                if (searchResult.size() < 1) {
                    mStopAll = true;
                }
                msg.what = SUCESSFUL;
                msg.obj = searchResult;
                msg.arg1 = page;
                msg.sendToTarget();
            }
        } catch (IOException e) {
            msg.what = ERROR;
            msg.obj = null;
            msg.sendToTarget();
            mStopAll = true;
        } catch (Exception e) {
            mStopAll = true;
        }
    }
}
