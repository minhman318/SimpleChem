package com.manminh.simplechem.model;

public class Result {
    private String mFormula;
    private String mCondition;
    private String mHowto;
    private String mPhenomenon;

    public Result(String mFormula, String mCondition, String mHowto, String mPhenomenon) {
        this.mFormula = mFormula;
        this.mCondition = mCondition;
        this.mHowto = mHowto;
        this.mPhenomenon = mPhenomenon;
    }

    public String getFormula() {
        return mFormula;
    }

    public String getCondition() {
        return mCondition;
    }

    public String getHowto() {
        return mHowto;
    }

    public String getPhenomenon() {
        return mPhenomenon;
    }
}
