package com.manminh.simplechem.balance;

import android.os.AsyncTask;

import com.manminh.simplechem.balance.engine.BalanceEngine;
import com.manminh.simplechem.exception.FailedBalanceException;
import com.manminh.simplechem.model.Equation;

public class BalanceTask extends AsyncTask<Equation, Void, Equation> {

    private BalanceEngine mEngine;
    private OnBlanceResultListener mHandler = null;
    private FailedBalanceException mException = null;

    public interface OnBlanceResultListener {
        void onSuccessful(Equation afterEquation);

        void onFailed(int why);
    }

    public BalanceTask(BalanceEngine engine) {
        mEngine = engine;
    }

    public BalanceTask(BalanceEngine engine, OnBlanceResultListener handler) {
        mEngine = engine;
        mHandler = handler;
    }

    public void setOnBalanceResultListener(OnBlanceResultListener listener) {
        mHandler = listener;
    }

    @Override
    protected void onPostExecute(Equation equation) {
        if (mException != null) {
            if (mHandler != null) mHandler.onFailed(mException.getExceptionCode());
        } else {
            if (mHandler != null) mHandler.onSuccessful(equation);
        }
    }

    @Override
    protected Equation doInBackground(Equation... equations) {
        try {
            mEngine.balance(equations[0]);
        } catch (FailedBalanceException ex) {
            mException = ex;
            return null;
        }
        return equations[0];
    }
}
