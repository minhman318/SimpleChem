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

    public String getmFormula() {
        return mFormula;
    }

    public String getmCondition() {
        return mCondition;
    }

    public String getmHowto() {
        return mHowto;
    }

    public String getmPhenomenon() {
        return mPhenomenon;
    }
}
