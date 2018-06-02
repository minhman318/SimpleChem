package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.manminh.simplechem.balance.model.Formula;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Formula f = Formula.parseFormula("Fe2O3");
        String k = f.toString();
    }
}
