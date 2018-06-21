package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.manminh.simplechem.balance.BalanceTool;
import com.manminh.simplechem.balance.engine.MathematicalBalanceEngine;
import com.manminh.simplechem.model.Chemical;
import com.manminh.simplechem.model.Equation;
<<<<<<< Updated upstream
import com.manminh.simplechem.model.Formula;
=======
import com.manminh.simplechem.search.SearchTool;
import com.manminh.simplechem.search.engine.PTHHSearchEngine;

import java.util.ArrayList;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< Updated upstream
        List<Chemical> before = new ArrayList<>();
        List<Chemical> after = new ArrayList<>();
        before.add(new Chemical(Formula.parseFormula("Fe")));
        before.add(new Chemical(Formula.parseFormula("H2SO4")));
        after.add(new Chemical(Formula.parseFormula("Fe2(SO4)3")));
        after.add(new Chemical(Formula.parseFormula("H2S")));
        after.add(new Chemical(Formula.parseFormula("H2O")));
        Equation eq = new Equation(before, after);
=======
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
>>>>>>> Stashed changes

        BalanceTool.balance(new MathematicalBalanceEngine(), eq);
        Log.d("RESULT", eq.toString());

    }
}
