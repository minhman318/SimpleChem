package com.manminh.simplechem.model;

import android.content.Context;

import com.manminh.simplechem.data.XmlDataManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Responsible for check an element is supported or not
 */
public class ElementDictionary {
    private static final int MAX_ELEMENT_STR_LENGTH = 2;

    // supported element names
    private static Set<String> mElementNameRef = null;

    public static void setUpData(Set<String> data) {
        mElementNameRef = data;
    }

    // check if an given element name is supported or not
    public static boolean isElement(String name) {
        if (mElementNameRef != null) {
            return name.length() <= MAX_ELEMENT_STR_LENGTH && mElementNameRef.contains(name);
        }
        return false;
    }
}
