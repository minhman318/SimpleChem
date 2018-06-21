package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.manminh.simplechem.balance.BalanceTool;
import com.manminh.simplechem.balance.engine.MathematicalBalanceEngine;
import com.manminh.simplechem.model.Chemical;
import com.manminh.simplechem.model.Equation;

import com.manminh.simplechem.search.SearchTool;
import com.manminh.simplechem.search.engine.PTHHSearchEngine;

public class MainActivity extends AppCompatActivity implements BalanceTool.OnBalanceResultListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BalanceTool tool = BalanceTool.getInstance();
        SearchTool mSearchTool = SearchTool.getInstance();
        mSearchTool.search(new PTHHSearchEngine(), "H2O", "");
        mSearchTool.getmResults();


        //tool.balance("H2 + O2 -> 2H2O", new MathematicalBalanceEngine(), this);
    }

    @Override
    public void onBalanceSuccessful(Equation result) {
        Log.d("BLANCE", result.toString());
    }

    @Override
    public void onHasBeenBalanced(Equation result) {
        Log.d("BALANCE", "No need balance, " + result.toString());
    }

    @Override
    public void onBalanceFailed() {
        Log.d("BLANCE", "Cannot balance");
    }

    @Override
    public void onParseEquationFailed(int exCode) {

    }

    @Override
    public void onParseFormulaFailed(int exCode) {

    }
}
