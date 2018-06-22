package com.manminh.simplechem.balance;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.manminh.simplechem.balance.engine.IBalanceEngine;
import com.manminh.simplechem.balance.exception.FailedBalanceException;
import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.balance.exception.ParseFormulaException;
import com.manminh.simplechem.model.Equation;

/**
 * Balance equation asynchronously
 * Singleton design pattern
 */
public class BalanceTool implements Runnable {

    /**
     * UI context should implements this interface to handle callbacks
     */
    public interface OnBalanceResultListener {
        void onBalanceSuccessful(Equation result);

        void onHasBeenBalanced(Equation result);

        void onBalanceFailed();

        void onParseEquationFailed(int exCode);

        void onParseFormulaFailed(int exCode);
    }

    private static final int BALANCE_SUCCESSFUL = 0;
    private static final int BALANCE_FAILED = 1;
    private static final int HAS_BEEN_BALANCED = 2;
    private static final int ILLEGAL_EQUATION = 3;
    private static final int ILLEGAL_FORMULA = 4;

    private String mEquationStr;
    private OnBalanceResultListener mListener;
    private IBalanceEngine mEngine;

    // Thread to do async task
    private Thread mWorker;

    // Only one instance
    private static final BalanceTool instance = new BalanceTool();

    // Get static instance
    public static BalanceTool getInstance() {
        return instance;
    }

    // Handler to notify main thread for updating UI
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int flag = msg.what;
            switch (flag) {
                case BALANCE_SUCCESSFUL:
                    mListener.onBalanceSuccessful((Equation) msg.obj);
                    break;
                case BALANCE_FAILED:
                    mListener.onBalanceFailed();
                    break;
                case HAS_BEEN_BALANCED:
                    mListener.onHasBeenBalanced((Equation) msg.obj);
                    break;
                case ILLEGAL_EQUATION:
                    mListener.onParseEquationFailed((int) msg.obj);
                    break;
                case ILLEGAL_FORMULA:
                    mListener.onParseFormulaFailed((int) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Balance an equation, performed callback when it had done
     *
     * @param equation String of equation ex: "H2 + O2 -> H2O"
     * @param engine   engine actually balance equation
     * @param listener should be UI context
     */
    public void balance(String equation, IBalanceEngine engine, OnBalanceResultListener listener) {
        mEquationStr = equation;
        mEngine = engine;
        mListener = listener;
        mWorker = new Thread(this); //  pass this since it implemented Runnable
        mWorker.start();
    }

    /**
     * run method of mWorker thread
     */
    @Override
    public void run() {
        Equation equation = null;
        Message msg = mHandler.obtainMessage();
        try {
            equation = Equation.parseEquation(mEquationStr);
            equation.verify();
            mEngine.balance(equation);

            // Well, balance successful
            msg.what = BALANCE_SUCCESSFUL;
            msg.obj = equation;
            msg.sendToTarget();

        } catch (ParseEquationException e) {
            ParseFormulaException cause = (ParseFormulaException) e.getCause();
            if (cause != null) {
                msg.what = ILLEGAL_FORMULA;
                msg.obj = cause.getExceptionCode();
                msg.sendToTarget();
            } else {
                msg.what = ILLEGAL_EQUATION;
                msg.obj = e.getExceptionCode();
                msg.sendToTarget();
            }
        } catch (FailedBalanceException e) {
            int exCode = e.getExceptionCode();
            if (exCode == FailedBalanceException.HAS_BEEN_BALANCED) {
                msg.what = HAS_BEEN_BALANCED;
                msg.obj = equation;
                msg.sendToTarget();
            } else if (exCode == FailedBalanceException.BALANCE_FAILED) {
                msg.what = BALANCE_FAILED;
                msg.sendToTarget();
            }
        } finally {
            mWorker.interrupt(); // no need this thread any more
            mWorker = null;
        }
    }
}
