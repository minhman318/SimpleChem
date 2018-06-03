package com.manminh.simplechem.model;

public class Chemical {
    private Formula mFormula;
    private int mFactor;

    public Chemical(Formula formula) {
        mFormula = formula;
        mFactor = -1;
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
