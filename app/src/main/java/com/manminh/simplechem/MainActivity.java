package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.manminh.simplechem.balance.BalanceTool;
import com.manminh.simplechem.balance.engine.MathematicalBalanceEngine;
import com.manminh.simplechem.model.Chemical;
import com.manminh.simplechem.model.Equation;
import com.manminh.simplechem.model.Formula;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Chemical> before = new ArrayList<>();
        List<Chemical> after = new ArrayList<>();
        before.add(new Chemical(Formula.parseFormula("Fe")));
        before.add(new Chemical(Formula.parseFormula("H2SO4")));
        after.add(new Chemical(Formula.parseFormula("Fe2(SO4)3")));
        after.add(new Chemical(Formula.parseFormula("SO2")));
        after.add(new Chemical(Formula.parseFormula("H2O")));
        Equation eq = new Equation(before, after);

        BalanceTool.balance(new MathematicalBalanceEngine(), eq);
        Log.d("RESULT", eq.toString());

        /*HashMap<String, Integer> logger = new HashMap<>();
        Formula f = Formula.parseFormula("H2(SO4)12");
        f.toString();
        f.logElement(logger, 1);*/

        /*Fraction a00 = new Fraction(0);
        Fraction a01 = new Fraction(2);
        Fraction a02 = new Fraction(-1);
        Fraction a03 = new Fraction(0);
        Fraction a10 = new Fraction(2);
        Fraction a11 = new Fraction(0);
        Fraction a12 = new Fraction(-2);
        Fraction a13 = new Fraction(0);
        Fraction a20 = new Fraction(0);
        Fraction a21 = new Fraction(0);
        Fraction a22 = new Fraction(1);
        Fraction a23 = new Fraction(1);

        Fraction[][] data = new Fraction[][]{{a00, a01, a02, a03}
                , {a10, a11, a12, a13}
                , {a20, a21, a22, a23}};

        MatrixResolver r = new MatrixResolver(data);
        Fraction[] res = r.solve();
        int[] res2 = Fraction.toIntegerEquation(res);*/

    }
}
