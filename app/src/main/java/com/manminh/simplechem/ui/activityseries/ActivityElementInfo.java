package com.manminh.simplechem.ui.activityseries;

import java.util.ArrayList;
import java.util.List;

public class ActivityElementInfo {
    private String mSymbol;
    private List<String> mAttrs;

    public ActivityElementInfo(String symbol) {
        mSymbol = symbol;
        mAttrs = new ArrayList<>();
    }

    public void addInfo(String attr) {
        mAttrs.add(attr);
    }

    public String getAttr(int pos) {
        return mAttrs.get(pos);
    }

    public String getSymbol() {
        return mSymbol;
    }

    public int attrsCount() {
        return mAttrs.size();
    }
}
