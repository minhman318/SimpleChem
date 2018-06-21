package com.manminh.simplechem.search;

import android.util.Log;

import com.manminh.simplechem.search.engine.SearchEngine;

import java.util.ArrayList;

public class SearchTool implements Runnable {

    private ArrayList<String> mResults;
    private SearchEngine mEngine;
    private String mIn;
    private String mOut;

    private Thread mWorker;
    public static final SearchTool instance = new SearchTool();
    public static SearchTool getInstance() {
        return instance;
    }

    public void search(SearchEngine engine, String in, String out) {
        mEngine = engine;
        mWorker = new Thread(this);
        mWorker.start();
        mIn = in;
        mOut = out;
    }

    @Override
    public void run() {
        try {
            mResults = mEngine.Search(mIn, mOut);
            Log.d("SEARCH", "okok" + mResults.size());
        } catch (Exception e) {
            Log.d("ERROR", "haiz");
        } finally {
            mWorker.interrupt(); // no need this thread any more
            mWorker = null;
        }
    }

    public ArrayList<String> getmResults() {
        return mResults;
    }
}
