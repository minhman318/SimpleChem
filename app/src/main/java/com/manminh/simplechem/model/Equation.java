package com.manminh.simplechem.model;

import com.manminh.simplechem.exception.ParseEquationException;
import com.manminh.simplechem.exception.ParseFormulaException;

import java.util.ArrayList;
import java.util.List;

public class Equation {
    // before chemical list
    private List<Chemical> mBefore;

    // after chemical list
    private List<Chemical> mAfter;

    // is balanced
    private boolean mIsBalanced = false;

    public static Equation parseEquation(String str) throws ParseEquationException {
        return new Equation(str);
    }

    public Equation(List<Chemical> before, List<Chemical> after) {
        mBefore = before;
        mAfter = after;
    }

    public Equation(String str) throws ParseEquationException {
        str = str.replace(" ", "");
        String regex;
        if (str.contains("->")) {
            regex = "->";
        } else if (str.contains("=")) {
            regex = "=";
        } else {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
        String[] chemicals;
        chemicals = str.split(regex);
        if (chemicals.length == 2) {
            String[] beforeStr = chemicals[0].split("\\+");
            String[] afterStr = chemicals[1].split("\\+");
            initData(beforeStr, afterStr);
        } else {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
    }

    private void initData(String[] beforeStr, String[] afterStr) throws ParseEquationException {
        mBefore = new ArrayList<>();
        mAfter = new ArrayList<>();

        if (beforeStr.length < 1 || afterStr.length < 1) {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
        try {
            for (String formulaStr : beforeStr) {
                Formula formula = Formula.parseFormula(formulaStr);
                Chemical chemical = new Chemical(formula);
                mBefore.add(chemical);
            }
            for (String formulaStr : afterStr) {
                Formula formula = Formula.parseFormula(formulaStr);
                Chemical chemical = new Chemical(formula);
                mAfter.add(chemical);
            }
        } catch (ParseFormulaException ex) {
            throw new ParseEquationException(ParseEquationException.INVALID_FORMULA, ex);
        }
    }

    public List<Chemical> getBefore() {
        return mBefore;
    }

    public List<Chemical> getAfter() {
        return mAfter;
    }

    public void markBalanced() {
        mIsBalanced = true;
    }

    public boolean isBalanced() {
        return mIsBalanced;
    }

    public int chemicalCount() {
        return mBefore.size() + mAfter.size();
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < mBefore.size() - 1; i++) {
            result += mBefore.get(i).toString() + " + ";
        }
        result += mBefore.get(mBefore.size() - 1).toString() + " -> ";
        for (int i = 0; i < mAfter.size() - 1; i++) {
            result += mAfter.get(i).toString() + " + ";
        }
        result += mAfter.get(mAfter.size() - 1).toString();
        return result;
    }
}
