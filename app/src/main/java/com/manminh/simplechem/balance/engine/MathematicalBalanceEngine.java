package com.manminh.simplechem.balance.engine;

import com.manminh.simplechem.exception.FailedBalanceException;
import com.manminh.simplechem.model.Chemical;
import com.manminh.simplechem.model.Equation;

import java.util.List;

/**
 * Implementation of mathematical balance algorithm
 */
public class MathematicalBalanceEngine implements BalanceEngine {

    /**
     * Balance an equation (equation will be modified)
     *
     * @param equation will be balanced
     * @throws FailedBalanceException if illegal equation or cannot solve matrix
     */
    @Override
    public void balance(Equation equation) throws FailedBalanceException {
        Fraction[][] data = buildData(equation);
        MatrixResolver resolver = new MatrixResolver(data);
        try {
            Fraction[] factors = resolver.solve(); // may throw ArithmeticException
            if (anyIsZero(factors)) {
                throw new FailedBalanceException(FailedBalanceException.BALANCE_FAILED);
            }
            if (factors.length == equation.chemicalCount()) {
                normalized(equation, factors);
                equation.markBalanced();
            }
        } catch (ArithmeticException e) {
            throw new FailedBalanceException(FailedBalanceException.BALANCE_FAILED, e);
        }
    }

    /**
     * Convert from fraction factors to integer factors and set equation's factors
     *
     * @param equation  will be modified (setFactor)
     * @param fractions is fraction factors
     */
    private void normalized(Equation equation, Fraction[] fractions) {
        int i = 0;
        int[] intFactor = Fraction.toIntegerEquation(fractions);
        for (Chemical chem : equation.getBefore()) {
            chem.setFactor(intFactor[i]);
            i++;
        }
        for (Chemical chem : equation.getAfter()) {
            chem.setFactor(intFactor[i]);
            i++;
        }
    }

    /**
     * Convert equation object to mathematical equations to solve (matrix)
     *
     * @param equation will be converted to 2D matrix
     * @return 2D fraction matrix
     * @throws FailedBalanceException if equation is illegal or equation has already been balanced
     */
    private Fraction[][] buildData(Equation equation) throws FailedBalanceException {

        // Check if the equation has already been balanced
        if (equation.isBalanced()) {
            throw new FailedBalanceException(FailedBalanceException.HAS_BEEN_BALANCED);
        }

        // Get all element names
        List<String> elementName = equation.getAllElementName();

        // Column of the matrix or variables number of equations
        int varCount = equation.beforeChemCount() + equation.afterChemCount();

        // The fraction matrix
        Fraction[][] data = new Fraction[varCount][varCount + 1];

        // Set the matrix
        for (int i = 0; i < varCount; i++) {
            if (i < elementName.size()) { // create a row by count elements, add to matrix
                String eName = elementName.get(i);
                Fraction[] row = new Fraction[varCount + 1];
                int k = 0;
                for (int j = 0; j < equation.beforeChemCount(); j++) {
                    int intFactor = equation.countBeforeElement(eName, j);
                    Fraction factor = new Fraction(intFactor);
                    row[k] = factor;
                    k++;
                }
                for (int j = 0; j < equation.afterChemCount(); j++) {
                    int intFactor = equation.countAfterElement(eName, j);
                    Fraction factor = new Fraction(intFactor);
                    row[k] = factor.changeSign();
                    k++;
                }
                row[varCount] = new Fraction(0);
                data[i] = row;
            } else {    // If not enough element, add some dummy row to matrix
                Fraction[] dummyRow = new Fraction[varCount + 1];
                for (int j = 0; j < varCount; j++) {
                    if (j == i) {
                        dummyRow[j] = new Fraction(1);
                    } else {
                        dummyRow[j] = new Fraction(0);
                    }
                }
                dummyRow[varCount] = new Fraction(-1);
                data[i] = dummyRow;
            }
        }
        return data;
    }

    private boolean anyIsZero(Fraction[] fractions) {
        for (Fraction frac : fractions) {
            if (frac.isZero()) return true;
        }
        return false;
    }
}
