package com.manminh.simplechem.model;

import java.util.List;

public class Equation {
    public static final String IVALID_EQUATION_MSG = "Invalid equation";

    private List<Chemical> mBefore;
    private List<Chemical> mAfter;
    private boolean mIsBalanced = false;

    public Equation(List<Chemical> before, List<Chemical> after) {
        mBefore = before;
        mAfter = after;
    }

    public List<Chemical> getBefore() {
        return mBefore;
    }

    public List<Chemical> getAfter() {
        return mAfter;
    }

    public void markBalanced() {
        mIsBalanced = true;
    }

    public boolean isBalanced() {
        return mIsBalanced;
    }

    public int chemicalCount() {
        return mBefore.size() + mAfter.size();
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < mBefore.size() - 1; i++) {
            result += mBefore.get(i).toString() + " + ";
        }
        result += mBefore.get(mBefore.size() - 1).toString() + " -> ";
        for (int i = 0; i < mAfter.size() - 1; i++) {
            result += mAfter.get(i).toString() + " + ";
        }
        result += mAfter.get(mAfter.size() - 1).toString();
        return result;
    }
}
