package com.manminh.simplechem.helpers;

public class ElementDictionary {
    private static final String[] Elements = {"Fe", "O", "S"};

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
}
