package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.manminh.simplechem.search.SearchTool;
import com.manminh.simplechem.search.engine.PTHHSearchEngine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchTool mTool = SearchTool.getInstance();

        mTool.search(new PTHHSearchEngine(), "Fe", "FeO");
    }
}
