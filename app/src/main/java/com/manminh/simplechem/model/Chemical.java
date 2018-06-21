package com.manminh.simplechem.model;

/**
 * Represents a chemical in an equation
 * Chemical has a formula and a factor
 * Ex: "3O2" -> "O2" is formula and 3 is factor
 */
public class Chemical {

    private Formula mFormula;
    private int mFactor = 1;

    public Chemical(Formula formula) {
        mFormula = formula;
    }

    public void setFactor(Integer factor) {
        mFactor = factor;
    }

    public Integer getFactor() {
        return mFactor;
    }

    public Formula getFormula() {
        return mFormula;
    }

    public String toString() {
        if (mFactor != -1 && mFactor != 1) {
            return String.valueOf(mFactor) + mFormula.toString();
        }
        return mFormula.toString();
    }
}
