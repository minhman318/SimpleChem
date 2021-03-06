package com.manminh.simplechem.model;

import android.util.Pair;

import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.balance.exception.ParseFormulaException;

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
    private List<Chemical> mAfChemicals;

    // One map for each before chemicals to count number of elements
    private List<Map<String, Integer>> mBfElementMapList = new ArrayList<>();

    // One map for each after chemicals to count number of elements
    private List<Map<String, Integer>> mAfElementMapList = new ArrayList<>();

    // Name of all before elements
    private Set<String> mBfElementNameList = new HashSet<>();

    // Name of all after elements
    private Set<String> mAfElementNameList = new HashSet<>();

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
        mAfChemicals = new ArrayList<>();

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
                mAfElementNameList.addAll(logger.keySet());
                mAfElementMapList.add(logger);
                mAfChemicals.add(chemical);
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
            for (int i = 0; i < mAfElementMapList.size(); i++) {
                int factor = mAfChemicals.get(i).getFactor();
                afterCount += factor * countElement(name, mAfElementMapList.get(i));
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
        if (!mBfElementNameList.containsAll(mAfElementNameList)) {
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
        return mAfChemicals;
    }

    public void markBalanced() {
        mIsBalanced = true;
    }

    public boolean isBalanced() {
        return mIsBalanced;
    }

    public int chemicalCount() {
        return mBfChemicals.size() + mAfChemicals.size();
    }

    public int beforeChemCount() {
        return mBfChemicals.size();
    }

    public int afterChemCount() {
        return mAfChemicals.size();
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
        if (mAfElementMapList.get(which).containsKey(name)) {
            return mAfElementMapList.get(which).get(name);
        }
        return 0;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mBfChemicals.size() - 1; i++) {
            result.append(mBfChemicals.get(i).toString()).append(" + ");
        }
        result.append(mBfChemicals.get(mBfChemicals.size() - 1).toString()).append(" ").append(mSimpleEquation.getRegex()).append(" ");
        for (int i = 0; i < mAfChemicals.size() - 1; i++) {
            result.append(mAfChemicals.get(i).toString()).append(" + ");
        }
        result.append(mAfChemicals.get(mAfChemicals.size() - 1).toString());
        return result.toString();
    }

    public String toHtmlSpannedStr() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mBfChemicals.size() - 1; i++) {
            result.append(mBfChemicals.get(i).toHtmlString()).append(" + ");
        }
        result.append(mBfChemicals.get(mBfChemicals.size() - 1).toHtmlString()).append(" ").append(mSimpleEquation.getRegex()).append(" ");
        for (int i = 0; i < mAfChemicals.size() - 1; i++) {
            result.append(mAfChemicals.get(i).toHtmlString()).append(" + ");
        }
        result.append(mAfChemicals.get(mAfChemicals.size() - 1).toHtmlString());
        return result.toString();
    }
}
