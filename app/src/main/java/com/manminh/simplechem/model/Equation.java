package com.manminh.simplechem.model;

import android.util.Pair;

import com.manminh.simplechem.exception.ParseEquationException;
import com.manminh.simplechem.exception.ParseFormulaException;

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

    public static Equation parseEquation(String str) throws ParseEquationException {
        return new Equation(str);
    }

    /**
     * Constructor form string equation
     *
     * @param str string equation
     * @throws ParseEquationException if parse equation failed
     */
    public Equation(String str) throws ParseEquationException {
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
            String[] beforeStr = chemicals[0].split("\\+");
            String[] afterStr = chemicals[1].split("\\+");
            initData(beforeStr, afterStr);
        } else {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
    }

    /**
     * Initialize attributes of Equation class (this)
     *
     * @param beforeStr list of before chemical string
     * @param afterStr  list of after chemical string
     * @throws ParseEquationException when parse any formula string failed
     */
    private void initData(String[] beforeStr, String[] afterStr)
            throws ParseEquationException {

        mBfChemicals = new ArrayList<>();
        mAtChemicals = new ArrayList<>();

        if (beforeStr.length < 1 || afterStr.length < 1) {
            throw new ParseEquationException(ParseEquationException.INVALID_SYNTAX);
        }
        try {
            for (String formulaStr : beforeStr) {
                Chemical chemical = buildChemical(formulaStr);
                HashMap<String, Integer> logger = new HashMap<>();
                chemical.getFormula().logElement(logger, 1);
                mBfElementNameList.addAll(logger.keySet());
                mBfElementMapList.add(logger);
                mBfChemicals.add(chemical);
            }
            for (String formulaStr : afterStr) {
                Chemical chemical = buildChemical(formulaStr);
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
     * Create Chemical object form formula string
     *
     * @param formulaStr given formula string
     * @return Chemical object
     * @throws ParseFormulaException if parse formula failed
     */
    private Chemical buildChemical(String formulaStr) throws ParseFormulaException {
        Pair<String, Integer> temp = extractFactor(formulaStr);
        Formula formula;
        Chemical chemical;
        if (temp != null) {
            formula = Formula.parseFormula(temp.first);
            chemical = new Chemical(formula);
            chemical.setFactor(temp.second);
        } else {
            formula = Formula.parseFormula(formulaStr);
            chemical = new Chemical(formula);
        }
        return chemical;
    }

    /**
     * If formula string has a factor (Ex: "2H2SO4"), return the pair "H2SO4" and 2
     *
     * @param formula formula string
     * @return Pair: first is right formula string, second is the factor
     * null if there's no factor in formula string (Ex: "H2O")
     */
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
        return null;
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
}
