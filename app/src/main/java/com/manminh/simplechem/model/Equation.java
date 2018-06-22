package com.manminh.simplechem.model;

import android.text.Html;
import android.text.Spanned;
import android.util.Pair;

import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.balance.exception.ParseFormulaException;

import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an equation
 */
public class Equation {

    // Exception message
    public static final String ILLEGAL_EQUATION_EX = "Illegal equation";

    // Before chemical list
    private List<Chemical> mBfChemicals;

    // After chemical list
    private List<Chemical> mAtChemicals;

    // One map for each before chemicals to count number of elements
    private List<Map<String, Integer>> mBfElementMapList = new ArrayList<>();

    // One map for each after chemicals to count number of elements
    private List<Map<String, Integer>> mAtElementMapList = new ArrayList<>();

    // Name of all before elements
    private Set<String> mBfElementNameList = new HashSet<>();

    // Name of all after elements
    private Set<String> mAtElementNameList = new HashSet<>();

    // is balanced
    private boolean mIsBalanced = false;

    private SimpleEquation mSimpleEquation;

    public static Equation parseEquation(String str) throws ParseEquationException {
        return new Equation(new SimpleEquation(str));
    }

    public static Equation parseEquation(SimpleEquation simpleEquation) throws ParseEquationException {
        return new Equation(simpleEquation);
    }

    public Equation(SimpleEquation simpleEquation) throws ParseEquationException {
        mSimpleEquation = simpleEquation;
        mBfChemicals = new ArrayList<>();
        mAtChemicals = new ArrayList<>();
        try {
            for (Pair<String, Integer> p : simpleEquation.getBeforeChemicals()) {
                Chemical chemical = buildChemical(p);
                HashMap<String, Integer> logger = new HashMap<>();
                chemical.getFormula().logElement(logger, 1);
                mBfElementNameList.addAll(logger.keySet());
                mBfElementMapList.add(logger);
                mBfChemicals.add(chemical);
            }
            for (Pair<String, Integer> p : simpleEquation.getAfterChemicals()) {
                Chemical chemical = buildChemical(p);
                HashMap<String, Integer> logger = new HashMap<>();
                chemical.getFormula().logElement(logger, 1);
                mAtElementNameList.addAll(logger.keySet());
                mAtElementMapList.add(logger);
                mAtChemicals.add(chemical);
            }

            // Check balance state
            if (checkBalanced()) {
                this.markBalanced(); // is balanced
            }

        } catch (ParseFormulaException e) {
            throw new ParseEquationException(ParseEquationException.INVALID_EQUATION, e);
        }
    }

    private Chemical buildChemical(Pair<String, Integer> comp) throws ParseFormulaException {
        Formula formula = Formula.parseFormula(comp.first);
        Chemical chemical = new Chemical(formula);
        chemical.setFactor(comp.second);
        return chemical;
    }

    /**
     * Check balance state
     *
     * @return true if equation has already been balanced
     */
    private boolean checkBalanced() {
        for (String name : mBfElementNameList) {
            int beforeCount = 0;
            int afterCount = 0;
            for (int i = 0; i < mBfElementMapList.size(); i++) {
                int factor = mBfChemicals.get(i).getFactor();
                beforeCount += factor * countElement(name, mBfElementMapList.get(i));
            }
            for (int i = 0; i < mAtElementMapList.size(); i++) {
                int factor = mAtChemicals.get(i).getFactor();
                afterCount += factor * countElement(name, mAtElementMapList.get(i));
            }
            if (beforeCount != afterCount) return false;
        }
        return true;
    }

    /**
     * Verify legal equation
     *
     * @throws IllegalArgumentException before element is not correspond to after element
     */
    public void verify() throws ParseEquationException {
        if (!mBfElementNameList.containsAll(mAtElementNameList)) {
            throw new ParseEquationException(ParseEquationException.INVALID_EQUATION);
        }
    }

    private int countElement(String name, Map<String, Integer> map) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        return 0;
    }

    public List<Chemical> getBefore() {
        return mBfChemicals;
    }

    public List<Chemical> getAfter() {
        return mAtChemicals;
    }

    public void markBalanced() {
        mIsBalanced = true;
    }

    public boolean isBalanced() {
        return mIsBalanced;
    }

    public int chemicalCount() {
        return mBfChemicals.size() + mAtChemicals.size();
    }

    public int beforeChemCount() {
        return mBfChemicals.size();
    }

    public int afterChemCount() {
        return mAtChemicals.size();
    }

    public SimpleEquation getSimpleEquation() {
        return mSimpleEquation;
    }

    public List<String> getAllElementName() {
        return new ArrayList<>(mBfElementNameList);
    }

    public int countBeforeElement(String name, int which) {
        if (mBfElementMapList.get(which).containsKey(name)) {
            return mBfElementMapList.get(which).get(name);
        }
        return 0;
    }

    public int countAfterElement(String name, int which) {
        if (mAtElementMapList.get(which).containsKey(name)) {
            return mAtElementMapList.get(which).get(name);
        }
        return 0;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < mBfChemicals.size() - 1; i++) {
            result += mBfChemicals.get(i).toString() + " + ";
        }
        result += mBfChemicals.get(mBfChemicals.size() - 1).toString() + " -> ";
        for (int i = 0; i < mAtChemicals.size() - 1; i++) {
            result += mAtChemicals.get(i).toString() + " + ";
        }
        result += mAtChemicals.get(mAtChemicals.size() - 1).toString();
        return result;
    }

    public Spanned toHtmlSpanned() {
        String result = "";
        for (int i = 0; i < mBfChemicals.size() - 1; i++) {
            result += mBfChemicals.get(i).toHtmlString() + " + ";
        }
        result += mBfChemicals.get(mBfChemicals.size() - 1).toHtmlString() + Html.fromHtml("&rarr");
        for (int i = 0; i < mAtChemicals.size() - 1; i++) {
            result += mAtChemicals.get(i).toHtmlString() + " + ";
        }
        result += mAtChemicals.get(mAtChemicals.size() - 1).toHtmlString();
        return Html.fromHtml(result);
    }
}
