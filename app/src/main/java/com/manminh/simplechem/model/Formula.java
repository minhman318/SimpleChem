package com.manminh.simplechem.model;

import android.support.annotation.NonNull;

import com.manminh.simplechem.balance.exception.ParseFormulaException;

import java.util.Map;

/**
 * Represent a formula
 * Composite design pattern
 */
public abstract class Formula {
    // defaut subscript
    private int mSubscript = 1;

    // count number of an element in the formula
    public abstract int countElement(String symbol);

    // count all element and save to given map
    public abstract void logElement(@NonNull Map<String, Integer> map, int factor);

    public abstract String toString();

    public abstract String toHtmlString();

    public void setSubscript(int srt) {
        mSubscript = srt;
    }

    public int getSubscript() {
        return mSubscript;
    }

    // parse string to formula, throw ParseException
    public static Formula parseFormula(String str) throws ParseFormulaException {
        if (str == null || str.length() < 1) {
            throw new ParseFormulaException(ParseFormulaException.EMPTY_STRING);
        }
        int i = str.length() - 1;
        while (i >= 0 && Character.isDigit(str.charAt(i))) {
            i--;
        }
        String testStr = str;
        String factorStr = null;
        if (i != str.length() - 1) {
            testStr = str.substring(0, i + 1);
            factorStr = str.substring(i + 1);
        }
        if (testStr.length() < 3) {
            if (ElementDictionary.isElement(testStr)) {
                Formula f = new SingleFormula(testStr);
                if (factorStr != null) {
                    int factor = Integer.parseInt(factorStr);
                    f.setSubscript(factor);
                }
                return f;
            } else if (testStr.length() == 1) {
                throw new ParseFormulaException(ParseFormulaException.INVALID_ELEMENT);
            }
        }
        return new CompoundFormula(str, 0);
    }
}
