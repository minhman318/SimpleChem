package com.manminh.simplechem.balance.model;

public class SimpleFormula extends Formula {
    private Element mElement;

    public SimpleFormula(String str) {
        mElement = new Element(str);
    }

    @Override
    public String toString() {
        if (getSubscript() == 1) return mElement.getSymbol();
        return mElement.getSymbol() + String.valueOf(getSubscript());
    }
}