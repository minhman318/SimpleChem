package com.manminh.simplechem.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ElementDictionary {

    private static Set<String> mElementRef = new HashSet<>(Arrays.asList(
            "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg",
            "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr",
            "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br",
            "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd",
            "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba", "Hf",
            "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi"));

    public static boolean isElement(String name) {
        return mElementRef.contains(name) && name.length() < 3;
    }

    public static boolean isElementAndThrowExceptionIfNot(String name) {
        if (mElementRef.contains(name)) {
            return true;
        }
        throw new IllegalArgumentException(Formula.ELEMENT_NOT_SUPPORTED_MSG);
    }
}
