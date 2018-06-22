package com.manminh.simplechem.search;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.manminh.simplechem.model.Result;
import com.manminh.simplechem.search.engine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class SearchTool implements Runnable {
    private static final int ERROR = 0;
    private static final int SUCESSFUL = 1;

    public interface OnSearchResult {
        void onResult(List<Result> results);

        void onError();
    }

    private SearchEngine mEngine;
    private String mIn;
    private String mOut;

    private OnSearchResult mListener;

    private Thread mWorker;

    private static final SearchTool instance = new SearchTool();
    public static SearchTool getInstance() {
        return instance;
    }

    public void search(SearchEngine engine, String in, String out, OnSearchResult listener) {
        mEngine = engine;
        mIn = in;
        mOut = out;
        mListener = listener;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCESSFUL:
                    ArrayList<Result> results = (ArrayList<Result>) msg.obj;
                    mListener.onResult(results);
                    break;
                case ERROR:
                    mListener.onError();
                    break;
                default:
                    break;
            }
        }
    };

    public void search() {
        mWorker = new Thread(this);
        mWorker.start();
    }

    @Override
    public void run() {
        Message msg = mHandler.obtainMessage();
        try {
            ArrayList<Result> result = mEngine.Search(mIn, mOut);
            msg.what = SUCESSFUL;
            msg.obj = result;
        } catch (Exception e) {
            msg.what = ERROR;
            msg.obj = null;
        } finally {
            mWorker.interrupt(); // no need this thread any more
            mWorker = null;
        }
    }
}
