package com.manminh.simplechem.model;

import android.support.annotation.NonNull;

import java.util.Map;

public class SimpleFormula extends Formula {
    private Element mElement;

    public SimpleFormula(String str) {
        if (ElementDictionary.isElementAndThrowExceptionIfNot(str)) {
            mElement = new Element(str);
        }
    }

    @Override
    public String toString() {
        if (getSubscript() == 1) return mElement.getSymbol();
        return mElement.getSymbol() + String.valueOf(getSubscript());
    }

    @Override
    public int countElement(String symbol) {
        if (mElement.getSymbol().equals(symbol)) {
            return getSubscript();
        }
        return 0;
    }

    @Override
    public void logElement(@NonNull Map<String, Integer> map, int factor) {
        String key = mElement.getSymbol();
        if (!map.containsKey(key)) {
            map.put(key, getSubscript() * factor);
        } else {
            map.put(key, map.get(key) + getSubscript() * factor);
        }
    }
}