package com.manminh.simplechem.model;

import android.text.Html;
import android.util.Pair;

import com.manminh.simplechem.balance.exception.ParseEquationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents equation, chemicals is string with a factor
 */
public class SimpleEquation {

    // List of Pair<formula string, factor>
    private List<Pair<String, Integer>> mBfChemicals;
    private List<Pair<String, Integer>> mAfChemicals;

    private String mRegex;

    /**
     * Init from string
     *
     * @param str ex: "H2+O2->H2O"
     * @throws ParseEquationException
     */
    public SimpleEquation(String str) throws ParseEquationException {
        str = str.replace(" ", "");
        String regex;
        if (str.contains("->")) {
            regex = "->";
            mRegex = Html.fromHtml("&rarr").toString();
        } else if (str.contains("=")) {
            regex = "=";
            mRegex = "=";
        } else if (str.contains(Html.fromHtml("&rarr").toString())) {
            regex = Html.fromHtml("&rarr").toString();
            mRegex = regex;
        } else if (str.contains(Html.fromHtml("&harr").toString())) {
            regex = Html.fromHtml("&harr").toString();
            mRegex = regex;
        } else {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
        String[] chemicals;
        chemicals = str.split(regex);
        if (chemicals.length == 2) {
            mBfChemicals = new ArrayList<>();
            mAfChemicals = new ArrayList<>();

            String[] before = chemicals[0].split("\\+");
            String[] after = chemicals[1].split("\\+");

            if (before.length < 1 || after.length < 1) {
                throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
            }

            for (String s : before) {
                mBfChemicals.add(extractFactor(s));
            }
            for (String s : after) {
                mAfChemicals.add(extractFactor(s));
            }
        } else {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
    }

    /**
     * Init from database string
     *
     * @param beforeData ex: "2 H2 1 O2"
     * @param afterData  ex" 2 H2O"
     */
    public SimpleEquation(String beforeData, String afterData) {
        mBfChemicals = new ArrayList<>();
        mAfChemicals = new ArrayList<>();
        String[] s1 = beforeData.split(" ");
        for (int i = 0; i < s1.length * 2; i = i + 2) {
            String chem = s1[i + 1];
            int factor = Integer.parseInt(s1[i]);
            mBfChemicals.add(new Pair<>(chem, factor));
        }
        String[] s2 = afterData.split(" ");
        for (int i = 0; i < s2.length * 2; i = i + 2) {
            String chem = s2[i + 1];
            int factor = Integer.parseInt(s2[i]);
            mAfChemicals.add(new Pair<>(chem, factor));
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
        return mAfChemicals;
    }

    public boolean compareAndCopyFactor(SimpleEquation other) {
        int k = 0;
        int[] factors = new int[mBfChemicals.size() + mAfChemicals.size()];
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
        for (int i = 0; i < mAfChemicals.size(); i++) {
            String s1 = mAfChemicals.get(i).first;
            boolean has = false;
            int factor = 1;
            for (Pair<String, Integer> p2 : other.mAfChemicals) {
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
        for (int i = 0; i < mAfChemicals.size(); i++) {
            String f = mAfChemicals.get(i).first;
            mAfChemicals.set(i, new Pair<>(f, factors[k++]));
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Pair<String, Integer> p;
        for (int i = 0; i < mBfChemicals.size() - 1; i++) {
            p = mBfChemicals.get(i);
            result.append(String.valueOf(p.second)).append(p.first).append(" + ");
        }
        p = mBfChemicals.get(mBfChemicals.size() - 1);
        result.append(String.valueOf(p.second)).append(p.first).append(" -> ");
        for (int i = 0; i < mAfChemicals.size() - 1; i++) {
            p = mAfChemicals.get(i);
            result.append(String.valueOf(p.second)).append(p.first).append(" + ");
        }
        p = mAfChemicals.get(mAfChemicals.size() - 1);
        result.append(String.valueOf(p.second)).append(p.first).append(" -> ");
        return result.toString();
    }

    // Build data string to save to database
    public String getBeforeDataString() {
        StringBuilder res = new StringBuilder();
        int i;
        for (i = 0; i < mBfChemicals.size() - 1; i++) {
            Pair<String, Integer> p = mBfChemicals.get(i);
            res.append(p.first).append(" ");
        }
        res.append(mBfChemicals.get(i).first);
        return res.toString();
    }

    // Build data string to save to database
    public String getAfterDataString() {
        StringBuilder res = new StringBuilder();
        int i;
        for (i = 0; i < mAfChemicals.size() - 1; i++) {
            Pair<String, Integer> p = mAfChemicals.get(i);
            res.append(p.first).append(" ");
        }
        res.append(mAfChemicals.get(i).first);
        return res.toString();
    }

    public String getRegex() {
        return mRegex;
    }
}
