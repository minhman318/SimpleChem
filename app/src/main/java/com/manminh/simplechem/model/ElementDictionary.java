package com.manminh.simplechem.model;

public class ElementDictionary {
    private static final String[] Elements = {"Fe", "O", "S", "H", "Cu", "N", "C"};

    public static boolean isElement(String str) {
        if (str.length() > 2) {
            return false;
        }
        for (String e : Elements) {
            if (str.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isElementAndThrowExceptionIfNot(String str) {
        for (String e : Elements) {
            if (str.equals(e)) {
                return true;
            }
        }
        throw new IllegalArgumentException(Formula.ELEMENT_NOT_SUPPORTED_MSG);
    }
}
