package com.manminh.simplechem.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private String mEquation;
    private ArrayList<Detail> mDetails;

    public static class Builder {
        String mBEquation;
        ArrayList<Detail> mBDetails;

        public Builder(String eq) {
            mBEquation = eq;
            mBDetails = new ArrayList<>();
        }

        public SearchResult.Builder addDetail(String name, String content) {
            Detail detail = new Detail(name, content);
            mBDetails.add(detail);
            return this;
        }

        public SearchResult build() {
            return new SearchResult(this);
        }
    }

    public SearchResult(String eq, ArrayList<Detail> details) {
        mEquation = eq;
        mDetails = details;
    }

    public SearchResult(Builder builder) {
        mEquation = builder.mBEquation;
        mDetails = builder.mBDetails;
    }

    public String getEquation() {
        return mEquation;
    }

    public ArrayList<Detail> getDetails() {
        return mDetails;
    }
}
