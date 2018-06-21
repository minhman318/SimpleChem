package com.manminh.simplechem;

import com.manminh.simplechem.model.CompoundFormula;
import com.manminh.simplechem.model.Formula;
import com.manminh.simplechem.model.SingleFormula;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormulaTest {

    @Test
    public void initFormula() {
        final String str1 = "Fe2";
        final String str2 = "O12";
        final String str3 = "H2O";
        final String str4 = "Fe3O4";
        final String str5 = "CO";
        final String str6 = "CO2";

        Formula f1 = Formula.parseFormula(str1);
        Formula f2 = Formula.parseFormula(str2);
        Formula f3 = Formula.parseFormula(str3);
        Formula f4 = Formula.parseFormula(str4);
        Formula f5 = Formula.parseFormula(str5);
        Formula f6 = Formula.parseFormula(str6);

        assertTrue(f1 instanceof SingleFormula);
        assertTrue(f2 instanceof SingleFormula);
        assertTrue(f3 instanceof CompoundFormula);
        assertTrue(f4 instanceof CompoundFormula);
        assertTrue(f5 instanceof CompoundFormula);
        assertTrue(f6 instanceof CompoundFormula);
    }

}
