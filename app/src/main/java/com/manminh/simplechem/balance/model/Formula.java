package com.manminh.simplechem.balance.model;

import com.manminh.simplechem.helpers.ElementDictionary;

public abstract class Formula {
    private int mSubscript = 1;

    public abstract String toString();

    public void setSubscript(int srp) {
        mSubscript = srp;
    }

    public int getSubscript() {
        return mSubscript;
    }

    public static Formula parseFormula(String str) {
        int i = str.length() - 1;
        while (Character.isDigit(str.charAt(i))) {
            i--;
        }

        if (i < 1) {
            throw new IllegalArgumentException("invalid formula");
        }

        String testStr = str;
        if (i < str.length() - 1) {
            testStr = str.substring(0, i + 1);
            if (ElementDictionary.isElement(testStr)) {
                String subscript = str.substring(i + 1);
                SimpleFormula sf = new SimpleFormula(testStr);
                sf.setSubscript(Integer.parseInt(subscript));
                return sf;
            }
        } else return new SimpleFormula(testStr);
        return new CompoundFormula(str);
    }
}
