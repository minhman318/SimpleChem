package com.manminh.simplechem.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompoundFormula extends Formula {
    List<Formula> mFormulaList;

    public CompoundFormula(String str) throws IllegalArgumentException {
        mFormulaList = new ArrayList<>();
        valueOf(str);
    }

    private void valueOf(String str) {
        int i = 0;
        while (i < str.length()) {
            if (Character.isDigit(str.charAt(i))) {
                int s = i;
                String srtStr;
                while (i < str.length() && Character.isDigit(str.charAt(i))) {
                    i++;
                }
                if (s != i) {
                    srtStr = str.substring(s, i);
                } else {
                    srtStr = str.substring(s);
                }
                int srt = Integer.parseInt(srtStr);
                mFormulaList.get(mFormulaList.size() - 1).setSubscript(srt);
            } else if (Character.isAlphabetic(str.charAt(i))) {
                String testStr1 = str.substring(i, i + 1);
                if (i == str.length() - 1) {
                    if (ElementDictionary.isElementAndThrowExceptionIfNot(testStr1)) {
                        mFormulaList.add(new SimpleFormula(testStr1));
                        i++;
                    }
                } else {
                    String testStr2 = str.substring(i, i + 2);
                    if (ElementDictionary.isElement(testStr2)) {
                        mFormulaList.add(new SimpleFormula(testStr2));
                        i = i + 2;
                    } else if (ElementDictionary.isElementAndThrowExceptionIfNot(testStr1)) {
                        mFormulaList.add(new SimpleFormula(testStr1));
                        i++;
                    }
                }
            } else if (str.charAt(i) == '(') {
                int close = str.lastIndexOf(')');
                if (close != -1) {
                    String inside = str.substring(i + 1, close);
                    mFormulaList.add(new CompoundFormula(inside));
                    i = close + 1;
                } else {
                    throw new IllegalArgumentException(Formula.INVALID_FORMULA_MSG);
                }
            } else {
                throw new IllegalArgumentException(Formula.INVALID_FORMULA_MSG);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("");
        for (Formula f : mFormulaList) {
            if (f instanceof CompoundFormula && f.getSubscript() > 1) {
                strBuilder.append("(")
                        .append(f.toString())
                        .append(")")
                        .append(String.valueOf(f.getSubscript()));
            } else {
                strBuilder.append(f.toString());
            }
        }
        return strBuilder.toString();
    }

    @Override
    public int countElement(String symbol) {
        int res = 0;
        for (Formula f : mFormulaList) {
            res += f.countElement(symbol);
        }
        return res * this.getSubscript();
    }

    @Override
    public void logElement(@NonNull Map<String, Integer> map, int factor) {
        for (Formula f : mFormulaList) {
            f.logElement(map, getSubscript());
        }
    }
}
