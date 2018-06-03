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
    public void balance(Equation equation) {
        Fraction[][] data = buildData(equation);
        if (data != null) {
            MatrixResolver resolver = new MatrixResolver(data);
            Fraction[] factors = resolver.solve();
            if (factors.length == equation.chemicalCount()) {
                normalized(equation, factors);
                equation.markBalanced();
            }
        }
    }

    private void normalized(Equation equation, Fraction[] factors) {
        int i = 0;
        int[] realFactor = Fraction.toIntegerEquation(factors);
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

        List<Map<String, Integer>> beforeMapList = new ArrayList<>();
        List<Map<String, Integer>> afterMapList = new ArrayList<>();

        Set<String> beforeNameList = new HashSet<>();
        Set<String> afterNameList = new HashSet<>();

        for (int i = 0; i < before.size(); i++) {
            HashMap<String, Integer> logger = new HashMap<>();
            before.get(i).getFormula().logElement(logger, 1);
            beforeNameList.addAll(logger.keySet());
            beforeMapList.add(logger);
        }
        for (int i = 0; i < after.size(); i++) {
            HashMap<String, Integer> logger = new HashMap<>();
            after.get(i).getFormula().logElement(logger, 1);
            afterNameList.addAll(logger.keySet());
            afterMapList.add(logger);
        }

        if (!beforeNameList.containsAll(afterNameList)) {
            throw new IllegalArgumentException(Equation.IVALID_EQUATION_MSG);
        }

        if (isBalanced(beforeNameList, beforeMapList, afterMapList)) {
            return null;
        }

        List<String> elementName = new ArrayList<>(beforeNameList);
        int varCount = before.size() + after.size();
        Fraction[][] data = new Fraction[varCount][varCount + 1];

        for (int i = 0; i < varCount; i++) {
            if (i < elementName.size()) {
                String eName = elementName.get(i);
                Fraction[] row = new Fraction[varCount + 1];
                int k = 0;
                for (int j = 0; j < before.size(); j++) {
                    Fraction factor = new Fraction(countElement(eName, beforeMapList.get(j)));
                    row[k] = factor;
                    k++;
                }
                for (int j = 0; j < after.size(); j++) {
                    Fraction factor = new Fraction(countElement(eName, afterMapList.get(j)));
                    row[k] = factor.changeSign();
                    k++;
                }
                row[varCount] = new Fraction(0);
                data[i] = row;
            } else {
                Fraction[] dummyRow = new Fraction[varCount + 1];
                for (int j = 0; j < varCount; j++) {
                    if (j == i) {
                        dummyRow[j] = new Fraction(1);
                    } else {
                        dummyRow[j] = new Fraction(0);
                    }
                }
                dummyRow[varCount] = new Fraction(1);
                data[i] = dummyRow;
            }
        }
        return data;
    }

    private int countElement(String name, Map<String, Integer> map) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        return 0;
    }

    private boolean isBalanced(Set<String> nameList
            , List<Map<String, Integer>> beforeMapList
            , List<Map<String, Integer>> afterMapList) {

        for (String name : nameList) {
            int beforeCount = 0;
            int afterCount = 0;
            for (Map<String, Integer> map : beforeMapList) {
                beforeCount += countElement(name, map);
            }
            for (Map<String, Integer> map : afterMapList) {
                afterCount += countElement(name, map);
            }
            if (beforeCount != afterCount) return false;
        }
        return true;
    }
}
