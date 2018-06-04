package com.manminh.simplechem.model;

import android.support.annotation.NonNull;

import com.manminh.simplechem.exception.ParseFormulaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompoundFormula extends Formula {
    private List<Formula> mFormulaList;

    public CompoundFormula(String str, int pos) throws ParseFormulaException {
        mFormulaList = new ArrayList<>();
        if (str == null || str.length() < 1) {
            throw new ParseFormulaException(ParseFormulaException.EMPTY_STRING);
        }
        valueOf(str, pos);
    }

    private void valueOf(String str, int pos) throws ParseFormulaException {
        int i = 0;
        while (i < str.length()) {
            char ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                int s = i;
                String srtStr;
                while (i < str.length() && Character.isDigit(str.charAt(i))) {
                    i++;
                }
                srtStr = str.substring(s, i);
                int srt = Integer.parseInt(srtStr);
                mFormulaList.get(mFormulaList.size() - 1).setSubscript(srt);
            } else if (Character.isAlphabetic(ch)) {
                String testStr1 = str.substring(i, i + 1);
                if (i == str.length() - 1) {
                    if (ElementDictionary.isElement(testStr1)) {
                        mFormulaList.add(new SingleFormula(testStr1));
                        i++;
                    } else {
                        // wrong element name
                        throw new ParseFormulaException(ParseFormulaException.INVALID_ELEMENT);
                    }
                } else {
                    String testStr2 = str.substring(i, i + 2);
                    if (ElementDictionary.isElement(testStr2)) {
                        mFormulaList.add(new SingleFormula(testStr2));
                        i = i + 2;
                    } else if (ElementDictionary.isElement(testStr1)) {
                        mFormulaList.add(new SingleFormula(testStr1));
                        i++;
                    } else {
                        // wrong element name
                        throw new ParseFormulaException(ParseFormulaException.INVALID_ELEMENT);
                    }
                }
            } else if (ch == '(') {
                int close = str.lastIndexOf(')');
                if (close != -1) {
                    String inside = str.substring(i + 1, close);
                    mFormulaList.add(new CompoundFormula(inside, pos + i));
                    i = close + 1;
                } else {
                    // not found ')'
                    throw new ParseFormulaException(ParseFormulaException.INVALID_PARENTHESES);
                }
            } else {
                // contains illegal character
                throw new ParseFormulaException(ParseFormulaException.INVALID_CHARACTER);
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
