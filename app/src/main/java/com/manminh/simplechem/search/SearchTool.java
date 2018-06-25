package com.manminh.simplechem.search;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.manminh.simplechem.search.engine.PTHHSearchEngine;
import com.manminh.simplechem.search.engine.SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchTool implements Runnable {
    private static final int ERROR = 0;
    private static final int SUCESSFUL = 1;

    public interface OnSearchResult {
        void onResult(List<SearchResult> searchResults, int page);

        void onError();
    }

    private SearchEngine mEngine;
    private String mIn;
    private String mOut;

    private OnSearchResult mListener;

    private Integer mPage;
    private boolean mStopAll = false;

    public void search(SearchEngine engine, String in, String out, int item, int page, OnSearchResult listener) {
        mStopAll = false;
        mPage = page;
        mEngine = engine;
        mIn = in;
        mOut = out;
        mListener = listener;

        int loop;
        if (item % engine.getNumberItemsPerPage() == 0) {
            loop = item / engine.getNumberItemsPerPage();
        } else {
            loop = item / engine.getNumberItemsPerPage() + 1;
        }

        MyThreadPoolExecutor poolExecutor = MyThreadPoolExecutor.getInstance();
        for (int i = 0; i < loop; i++) {
            poolExecutor.execute(this);
        }
    }

    public void searchMore(int item, int page) {
        mStopAll = false;
        mPage = page;

        int loop;
        if (item % mEngine.getNumberItemsPerPage() == 0) {
            loop = item / mEngine.getNumberItemsPerPage();
        } else {
            loop = item / mEngine.getNumberItemsPerPage() + 1;
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
                    int page = msg.arg1;
                    mListener.onResult(searchResults, page);
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
                SearchEngine e = new PTHHSearchEngine();
                ArrayList<SearchResult> searchResult;

                int page;
                synchronized (mPage) {
                    page = mPage;
                    mPage++;
                }

                searchResult = e.Search(mIn, mOut, page);

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
