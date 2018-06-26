package com.manminh.simplechem.search;

import java.util.ArrayList;

/**
 * Represents search result, comprises an equation string and list of Detail objects
 */
public class SearchResult {
    private String mEquation;
    private ArrayList<EquationDetail> mDetails;

    // Builder design pattern
    public static class Builder {
        String mBEquation;
        ArrayList<EquationDetail> mBDetails;

        public Builder(String eq) {
            mBEquation = eq;
            mBDetails = new ArrayList<>();
        }

        public SearchResult.Builder addDetail(String name, String content) {
            EquationDetail detail = new EquationDetail(name, content);
            mBDetails.add(detail);
            return this;
        }

        public SearchResult build() {
            return new SearchResult(this);
        }
    }

    public SearchResult(Builder builder) {
        mEquation = builder.mBEquation;
        mDetails = builder.mBDetails;
    }

    public String getEquation() {
        return mEquation;
    }

    public ArrayList<EquationDetail> getDetails() {
        return mDetails;
    }
}
