package com.manminh.simplechem.search;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.manminh.simplechem.search.engine.SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchTool implements Runnable {
    private static final int ERROR = 0;
    private static final int SUCESSFUL = 1;

    public interface OnSearchResult {
        void onResult(List<SearchResult> searchResults);

        void onError();
    }

    private SearchEngine mEngine;
    private String mIn;
    private String mOut;

    private OnSearchResult mListener;

    private Thread mWorker;

    public void search(SearchEngine engine, String in, String out, OnSearchResult listener) {
        mEngine = engine;
        mIn = in;
        mOut = out;
        mListener = listener;
        mWorker = new Thread(this);
        mWorker.start();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCESSFUL:
                    ArrayList<SearchResult> searchResults = (ArrayList<SearchResult>) msg.obj;
                    mListener.onResult(searchResults);
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
            ArrayList<SearchResult> searchResult = mEngine.Search(mIn, mOut);
            msg.what = SUCESSFUL;
            msg.obj = searchResult;
            msg.sendToTarget();
        } catch (IOException e) {
            msg.what = ERROR;
            msg.obj = null;
            msg.sendToTarget();
        } finally {
            mWorker.interrupt(); // no need this thread any more
            mWorker = null;
        }
    }
}
