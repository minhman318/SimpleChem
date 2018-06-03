package com.manminh.simplechem.model;

import android.support.annotation.NonNull;

import java.util.Map;

public abstract class Formula {
    public static final String ELEMENT_NOT_SUPPORTED_MSG = "Element not supported";
    public static final String INVALID_FORMULA_MSG = "Invalid formula string";

    private int mSubscript = 1;

    public abstract String toString();

    public abstract int countElement(String symbol);

    public abstract void logElement(@NonNull Map<String, Integer> map, int factor);

    public void setSubscript(int srt) {
        mSubscript = srt;
    }

    public int getSubscript() {
        return mSubscript;
    }

    public static Formula parseFormula(String str) {
        if (str == null || str.length() < 1) {
            throw new IllegalArgumentException(INVALID_FORMULA_MSG);
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
        if (testStr.length() < 3 && ElementDictionary.isElement(testStr)) {
            Formula f = new SimpleFormula(testStr);
            if (factorStr != null) {
                int factor = Integer.parseInt(factorStr);
                f.setSubscript(factor);
            }
            return f;
        }
        return new CompoundFormula(str);
    }
}
