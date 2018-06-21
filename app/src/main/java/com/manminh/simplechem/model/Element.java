package com.manminh.simplechem.model;

/**
 * Element like H, O, C or S
 */
public class Element {

    private String mSymbol;

    public Element(String symbol) {
        mSymbol = symbol;
    }

    public String getSymbol() {
        return mSymbol;
    }

    @Override
    public int hashCode() {
        return mSymbol.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Element) {
            Element other = (Element) obj;
            return this.hashCode() == other.hashCode();
        } else {
            return false;
        }
    }
}
