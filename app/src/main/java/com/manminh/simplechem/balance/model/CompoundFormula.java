package com.manminh.simplechem.balance.model;

import com.manminh.simplechem.helpers.ElementDictionary;

import java.util.ArrayList;
import java.util.List;

public class CompoundFormula extends Formula {
    List<Formula> mFormulaList;

    public CompoundFormula(String str) throws IllegalArgumentException {
        mFormulaList = new ArrayList<>();
        valueOf(str);
    }

    @Override
    public String toString() {
        String str = "";
        for (Formula f : mFormulaList) {
            if (f instanceof CompoundFormula) {
                str += "(" + f.toString() + ")" + String.valueOf(f.getSubscript());
            } else {
                str += f.toString();
            }
        }
        return str;
    }

    private void valueOf(String str) {
        int i = 0;
        while (i < str.length()) {
            if (Character.isDigit(str.charAt(i))) {
                int s = i;
                while (i < str.length() && Character.isDigit(str.charAt(i))) {
                    i++;
                }
                String srtStr;
                if (s != i) {
                    srtStr = str.substring(s, i);
                } else {
                    srtStr = str.substring(s);
                }
                int srt = Integer.parseInt(srtStr);
                mFormulaList.get(mFormulaList.size() - 1).setSubscript(srt);
            } else if (Character.isAlphabetic(str.charAt(i))) {
                String testStr2 = str.substring(i, i + 2);
                String testStr1 = str.substring(i, i + 1);
                if (ElementDictionary.isElement(testStr2)) {
                    mFormulaList.add(new SimpleFormula(testStr2));
                    i = i + 2;
                } else if (ElementDictionary.isElement(testStr1)) {
                    mFormulaList.add(new SimpleFormula(testStr1));
                    i++;
                }
            } else if (str.charAt(i) == '(') {
                int end = str.lastIndexOf(')');
                String inside = str.substring(i + 1, end);
                mFormulaList.add(new CompoundFormula(inside));
                i = end + 1;
            }
        }
    }
}
