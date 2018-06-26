package com.manminh.simplechem.model;

import java.util.Set;

/**
 * Responsible for check an element is supported or not
 */
public class ElementDictionary {
    private static final int MAX_ELEMENT_STR_LENGTH = 2;

    // supported element names
    private static Set<String> mElementNameRef = null;

    // Init data is needed before any call to isElement
    public static void setUpData(Set<String> data) {
        mElementNameRef = data;
    }

    // check if an given element name is supported or not
    public static boolean isElement(String name) {
        return mElementNameRef != null && name.length()
                <= MAX_ELEMENT_STR_LENGTH && mElementNameRef.contains(name);
    }
}
