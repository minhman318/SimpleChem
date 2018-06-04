package com.manminh.simplechem.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ElementDictionary {
    public static final int MAX_ELEMENT_STR_LENGTH = 2;

    // supported element names
    private static Set<String> mElementNameRef = new HashSet<>(Arrays.asList(
            "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg",
            "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr",
            "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br",
            "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd",
            "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba", "Hf",
            "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi"));

    // check if an given element name is supported or not
    public static boolean isElement(String name) {
        return name.length() <= MAX_ELEMENT_STR_LENGTH && mElementNameRef.contains(name);
    }
}
