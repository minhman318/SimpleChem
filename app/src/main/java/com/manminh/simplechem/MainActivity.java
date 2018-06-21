package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import com.manminh.simplechem.balance.BalanceTool;
import com.manminh.simplechem.balance.MathematicalBalanceEngine;
import com.manminh.simplechem.model.Equation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.test);
        try {
            tv.setText((new Equation("2H2 + O2 -> H2(SO4)5")).toHtmlSpanned());
        } catch (Exception e) {

        }
    }
}
