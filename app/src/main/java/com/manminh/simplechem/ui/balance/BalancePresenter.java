package com.manminh.simplechem.ui.balance;

import com.manminh.simplechem.balance.BalanceTool;
import com.manminh.simplechem.balance.MathematicalBalanceEngine;
import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.balance.exception.ParseFormulaException;
import com.manminh.simplechem.model.Equation;
import com.manminh.simplechem.ui.base.BasePresenter;

public class BalancePresenter<V extends IBalanceView> extends BasePresenter<V> implements IBalancePresenter<V> {
    private BalanceTool mTool;

    public BalancePresenter() {
        mTool = new BalanceTool();
    }

    @Override
    public void balance(String equation) {
        mTool.balance(equation, new MathematicalBalanceEngine(), createHandler());
    }

    private BalanceTool.OnBalanceResultListener createHandler() {
        return new BalanceTool.OnBalanceResultListener() {
            @Override
            public void onBalanceSuccessful(Equation result) {
                getView().showResult(result.toString());
            }

            @Override
            public void onHasBeenBalanced(Equation result) {
                getView().showInfo("Nothing to do! This equation has been already balanced.");
            }

            @Override
            public void onBalanceFailed() {
                getView().showError("Sorry! We cannot balance this equation.");
            }

            @Override
            public void onParseEquationFailed(int exCode) {
                String msg;
                switch (exCode) {
                    case ParseEquationException.INVALID_EQUATION:
                        msg = "Equation is invalid. Please check the attendance of elements.";
                        break;
                    case ParseEquationException.INVALID_SYNTAX:
                        msg = "Equation syntax is invalid. Right syntax is \"A + B -> C + D\"";
                        break;
                    default:
                        msg = "Undefined error.";
                        break;
                }
                getView().showError(msg);
            }

            @Override
            public void onParseFormulaFailed(int exCode) {
                String msg;
                switch (exCode) {
                    case ParseFormulaException.EMPTY_STRING:
                        msg = "Formula cannot be empty.";
                        break;
                    case ParseFormulaException.INVALID_CHARACTER:
                        msg = "Invalid characters.";
                        break;
                    case ParseFormulaException.INVALID_ELEMENT:
                        msg = "Element symbol is wrong or not supported.";
                        break;
                    case ParseFormulaException.INVALID_PARENTHESES:
                        msg = "It seems that you missing \')\' somewhere.";
                        break;
                    default:
                        msg = "Undefined error.";
                        break;
                }
                getView().showError(msg);
            }
        };
    }
}
