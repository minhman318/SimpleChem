package com.manminh.simplechem.balance.engine;

import android.os.OperationCanceledException;
import android.support.annotation.NonNull;

public class MatrixResolver {
    public static final String CANNOT_SOLVE_MSG = "Cannot solve";
    private Fraction[][] mData;
    private int mRow;
    private int mCol;

    public MatrixResolver(@NonNull Fraction[][] data) {
        mRow = data.length;
        mCol = data[0].length;
        mData = data;
    }

    private void eliminate(int baseRow, int targetRow, int var) {
        Fraction bf = mData[baseRow][var];
        Fraction tf = mData[targetRow][var];
        if (tf.isZero()) {
            return;
        } else {
            if (bf.isZero()) {
                swapTwoRows(baseRow, targetRow);
                return;
            }
        }
        Fraction factor = bf.changeSign().divide(tf);
        mData[targetRow][var].setToZero();
        for (int i = var + 1; i < mData[baseRow].length; i++) {
            mData[targetRow][i] = mData[targetRow][i]
                    .multiply(factor)
                    .add(mData[baseRow][i]);
        }

    }

    private void gaussJordanEliminate() {
        if (mRow != mCol - 1) return;
        for (int i = 0; i < mRow - 1; i++) {
            for (int j = i + 1; j < mRow; j++) {
                eliminate(i, j, i);
            }
        }
    }

    public Fraction[] solve() {
        this.gaussJordanEliminate();
        Fraction[] result = new Fraction[mRow];
        for (int i = 0; i < mRow; i++) {
            if (mData[i][i].isZero()) {
                throw new OperationCanceledException(CANNOT_SOLVE_MSG);
            }
        }
        for (int i = mRow - 1; i >= 0; i--) {
            Fraction right = new Fraction();
            for (int j = mCol - 1; j > i; j--) {
                if (j == mCol - 1) {
                    right = right.add(mData[i][j]);
                } else {
                    Fraction t1 = mData[i][j].multiply(result[j]);
                    t1.changeSign();
                    right = right.add(mData[i][j].multiply(result[j]).changeSign());
                }
            }
            Fraction var = right.divide(mData[i][i]);
            result[i] = var;
        }
        return result;
    }

    private void swapTwoRows(int row1, int row2) {
        Fraction[] temp = mData[row1];
        mData[row1] = mData[row2];
        mData[row2] = temp;
    }
}
