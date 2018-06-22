package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.manminh.simplechem.model.Equation;
import com.manminh.simplechem.model.SimpleEquation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            SimpleEquation s1 = new SimpleEquation("2H2 + O2 -> 2H2O");
            SimpleEquation s2 = new SimpleEquation("O2 + H2 -> H2O");

            s2.compareAndCopyFactor(s1);

            TextView tv = findViewById(R.id.test);
            tv.setText((new Equation(s2)).toHtmlSpanned());
        } catch (Exception e) {

        }
    }
}
