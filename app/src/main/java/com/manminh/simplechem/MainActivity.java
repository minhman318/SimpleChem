package com.manminh.simplechem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.manminh.simplechem.balance.BalanceTask;
import com.manminh.simplechem.balance.engine.MathematicalBalanceEngine;
import com.manminh.simplechem.exception.ParseEquationException;
import com.manminh.simplechem.exception.ParseFormulaException;
import com.manminh.simplechem.model.Chemical;
import com.manminh.simplechem.model.Equation;
import com.manminh.simplechem.model.Formula;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //"H2SO4 + KMnO4 + FeSO4 -> Fe2(SO4)3 + H2 + MnSO4 + K2SO4"
        try {
            Equation eq = Equation.parseEquation("H2SO4 + KMnO4 + FeSO4 -> Fe2(SO4)3 + H2O + MnSO4 + K2SO4");
            BalanceTask task = new BalanceTask(new MathematicalBalanceEngine()
                    , new BalanceTask.OnBlanceResultListener() {
                @Override
                public void onSuccessful(Equation afterEquation) {
                    Log.d("RESULT", afterEquation.toString());
                }

                @Override
                public void onFailed(int why) {
                    Log.d("RESULT", "FAILED. CODE: " + String.valueOf(why));
                }
            });
            task.execute(eq);
        } catch (ParseEquationException ex) {
        }

    }
}
