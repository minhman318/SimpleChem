package com.manminh.simplechem.model;

import android.util.Pair;

import com.manminh.simplechem.balance.exception.ParseEquationException;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation {
    private List<Pair<String, Integer>> mBfChemicals;
    private List<Pair<String, Integer>> mAtChemicals;

    public SimpleEquation(String str) throws ParseEquationException {
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
            mBfChemicals = new ArrayList<>();
            mAtChemicals = new ArrayList<>();

            String[] before = chemicals[0].split("\\+");
            String[] after = chemicals[1].split("\\+");

            if (before.length < 1 || after.length < 1) {
                throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
            }

            for (String s : before) {
                mBfChemicals.add(extractFactor(s));
            }
            for (String s : after) {
                mAtChemicals.add(extractFactor(s));
            }
        } else {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
    }

    public SimpleEquation(String beforeData, String afterData) {
        mBfChemicals = new ArrayList<>();
        mAtChemicals = new ArrayList<>();
        String[] s1 = beforeData.split(" ");
        for (int i = 0; i < s1.length * 2; i = i + 2) {
            String chem = s1[i + 1];
            int factor = Integer.parseInt(s1[i]);
            mBfChemicals.add(new Pair<String, Integer>(chem, factor));
        }
        String[] s2 = afterData.split(" ");
        for (int i = 0; i < s2.length * 2; i = i + 2) {
            String chem = s2[i + 1];
            int factor = Integer.parseInt(s2[i]);
            mAtChemicals.add(new Pair<String, Integer>(chem, factor));
        }
    }

    private Pair<String, Integer> extractFactor(String formula) {
        int i = 0;
        while (Character.isDigit(formula.charAt(i))) {
            i++;
        }
        if (i != 0) {
            String numStr = formula.substring(0, i);
            int num = Integer.parseInt(numStr);
            return new Pair<>(formula.substring(i), num);
        }
        return new Pair<>(formula, 1);
    }

    public List<Pair<String, Integer>> getBeforeChemicals() {
        return mBfChemicals;
    }

    public List<Pair<String, Integer>> getAfterChemicals() {
        return mAtChemicals;
    }

    public boolean compareAndCopyFactor(SimpleEquation other) {
        int k = 0;
        int[] factors = new int[mBfChemicals.size() + mAtChemicals.size()];
        for (int i = 0; i < mBfChemicals.size(); i++) {
            String s1 = mBfChemicals.get(i).first;
            boolean has = false;
            int factor = 1;
            for (Pair<String, Integer> p2 : other.mBfChemicals) {
                String s2 = p2.first;
                if (s1.equals(s2)) {
                    has = true;
                    factor = p2.second;
                }
            }
            if (has) {
                factors[k++] = factor;
            } else {
                return false;
            }
        }
        for (int i = 0; i < mAtChemicals.size(); i++) {
            String s1 = mAtChemicals.get(i).first;
            boolean has = false;
            int factor = 1;
            for (Pair<String, Integer> p2 : other.mAtChemicals) {
                String s2 = p2.first;
                if (s1.equals(s2)) {
                    has = true;
                    factor = p2.second;
                }
            }
            if (has) {
                factors[k++] = factor;
            } else {
                return false;
            }
        }
        setFactors(factors);
        return true;
    }

    private void setFactors(int[] factors) {
        int k = 0;
        for (int i = 0; i < mBfChemicals.size(); i++) {
            String f = mBfChemicals.get(i).first;
            mBfChemicals.set(i, new Pair<>(f, factors[k++]));
        }
        for (int i = 0; i < mAtChemicals.size(); i++) {
            String f = mAtChemicals.get(i).first;
            mAtChemicals.set(i, new Pair<>(f, factors[k++]));
        }
    }

    public String toString() {
        String result = "";
        Pair<String, Integer> p;
        for (int i = 0; i < mBfChemicals.size() - 1; i++) {
            p = mBfChemicals.get(i);
            result += String.valueOf(p.second) + p.first + " + ";
        }
        p = mBfChemicals.get(mBfChemicals.size() - 1);
        result += String.valueOf(p.second) + p.first + " -> ";
        for (int i = 0; i < mAtChemicals.size() - 1; i++) {
            p = mAtChemicals.get(i);
            result += String.valueOf(p.second) + p.first + " + ";
        }
        p = mAtChemicals.get(mAtChemicals.size() - 1);
        result += String.valueOf(p.second) + p.first + " -> ";
        return result;
    }

    public String getBeforeDataString() {
        String res = "";
        int i;
        for (i = 0; i < mBfChemicals.size() - 1; i++) {
            Pair<String, Integer> p = mBfChemicals.get(i);
            res += p.first + " ";
        }
        res += mBfChemicals.get(i).first;
        return res;
    }


    public String getAfterDataString() {
        String res = "";
        int i;
        for (i = 0; i < mAtChemicals.size() - 1; i++) {
            Pair<String, Integer> p = mAtChemicals.get(i);
            res += p.first + " ";
        }
        res += mAtChemicals.get(i).first;
        return res;
    }
}
