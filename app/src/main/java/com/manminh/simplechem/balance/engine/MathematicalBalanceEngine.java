package com.manminh.simplechem.balance.engine;

import com.manminh.simplechem.model.Chemical;
import com.manminh.simplechem.model.Equation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MathematicalBalanceEngine implements BalanceEngine {

    @Override
    public boolean balance(Equation equation) {
        Fraction[][] data = buildData(equation);
        if (data != null) {
            MatrixResolver resolver = new MatrixResolver(data);
            Fraction[] result = resolver.solve();
            if (result.length == equation.chemicalCount()) {
                normalized(equation, result);
                equation.markBalanced();
                return true;
            }
        }
        return false;
    }

    private void normalized(Equation equation, Fraction[] result) {
        int i = 0;
        int[] realFactor = Fraction.toIntegerEquation(result);
        for (Chemical chem : equation.getBefore()) {
            chem.setFactor(realFactor[i]);
            i++;
        }
        for (Chemical chem : equation.getAfter()) {
            chem.setFactor(realFactor[i]);
            i++;
        }
    }

    private Fraction[][] buildData(Equation equation) {

        List<Chemical> before = equation.getBefore();
        List<Chemical> after = equation.getAfter();

        List<Map<String, Integer>> beforeElementRef = new ArrayList<>();
        List<Map<String, Integer>> afterElementRef = new ArrayList<>();

        Set<String> beforeElementName = new HashSet<>();
        Set<String> afterElementName = new HashSet<>();

        for (int i = 0; i < before.size(); i++) {
            HashMap<String, Integer> logger = new HashMap<>();
            before.get(i).getFormula().logElement(logger, 1);
            beforeElementName.addAll(logger.keySet());
            beforeElementRef.add(logger);
        }
        for (int i = 0; i < after.size(); i++) {
            HashMap<String, Integer> logger = new HashMap<>();
            after.get(i).getFormula().logElement(logger, 1);
            afterElementName.addAll(logger.keySet());
            afterElementRef.add(logger);
        }

        if (!beforeElementName.containsAll(afterElementName)) {
            throw new IllegalArgumentException(Equation.IVALID_EQUATION_MSG);
        }

        beforeElementName.addAll(afterElementName);
        List<String> elementName = new ArrayList<>(beforeElementName);

        int varCount = before.size() + after.size();

        Fraction[][] data = new Fraction[varCount][varCount + 1];

        for (int i = 0; i < varCount; i++) {
            if (i < elementName.size()) {
                String eName = elementName.get(i);
                Fraction[] temp = new Fraction[varCount + 1];
                int k = 0;
                for (int j = 0; j < before.size(); j++) {
                    Fraction factor = new Fraction(countElement(eName, beforeElementRef.get(j)));
                    temp[k] = factor;
                    k++;
                }
                for (int j = 0; j < after.size(); j++) {
                    Fraction factor = new Fraction(countElement(eName, afterElementRef.get(j)));
                    temp[k] = factor.changeSign();
                    k++;
                }
                temp[varCount] = new Fraction(0);
                data[i] = temp;
            } else {
                Fraction[] dummy = new Fraction[varCount + 1];
                for (int j = 0; j < varCount; j++) {
                    if (j == i) {
                        dummy[j] = new Fraction(1);
                    } else {
                        dummy[j] = new Fraction(0);
                    }
                }
                dummy[varCount] = new Fraction(1);
                data[i] = dummy;
            }
        }
        return data;
    }

    private int countElement(String name, Map<String, Integer> ref) {
        if (ref.containsKey(name)) {
            return ref.get(name);
        }
        return 0;
    }
}
