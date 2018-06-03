package com.manminh.simplechem.balance.engine;

public class Fraction {
    private int mNumerator;
    private int mDenominator;

    public Fraction() {
        mNumerator = 0;
        mDenominator = 1;
    }

    public Fraction(int num) {
        mNumerator = num;
        mDenominator = 1;
    }

    public Fraction(int numerator, int denominator) {
        mNumerator = numerator;
        mDenominator = denominator;
    }

    public Fraction(Fraction frac) {
        mNumerator = frac.mNumerator;
        mDenominator = frac.mDenominator;
    }

    public Fraction changeSign() {
        Fraction temp = new Fraction(this);
        temp.mNumerator = -temp.mNumerator;
        return temp;
    }

    public Fraction add(Fraction other) {
        Fraction result = new Fraction();
        result.mNumerator = this.mNumerator * other.mDenominator + this.mDenominator * other.mNumerator;
        result.mDenominator = this.mDenominator * other.mDenominator;
        result.normilized();
        return result;
    }

    public Fraction multiply(Fraction other) {
        Fraction result = new Fraction();
        result.mNumerator = this.mNumerator * other.mNumerator;
        result.mDenominator = this.mDenominator * other.mDenominator;
        result.normilized();
        return result;
    }

    public Fraction multiply(int number) {
        Fraction result = new Fraction();
        result.mNumerator = this.mNumerator * number;
        result.mDenominator = this.mDenominator;
        result.normilized();
        return result;
    }

    public Fraction divide(Fraction other) {
        Fraction result = new Fraction();
        result.mNumerator = this.mNumerator * other.mDenominator;
        result.mDenominator = this.mDenominator * other.mNumerator;
        result.normilized();
        return result;
    }

    public void normilized() {
        if (mDenominator < 0) {
            mNumerator = -mNumerator;
            mDenominator = -mDenominator;
        }
        int gcd = computeGcd(mNumerator, mDenominator);
        if (gcd > 0) {
            mNumerator /= gcd;
            mDenominator /= gcd;
        }
    }

    public void setToZero() {
        mNumerator = 0;
        mDenominator = 1;
    }

    public boolean isZero() {
        return mNumerator == 0;
    }

    public String toString() {
        if (mDenominator != 1) {
            return String.valueOf(mNumerator) + "/" + String.valueOf(mDenominator);
        }
        return String.valueOf(mNumerator);
    }

    public static int computeGcd(int a, int b) {
        if (a == 0)
            return b;
        return computeGcd(b % a, a);
    }

    public static int computeLcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return (a * b) / computeGcd(a, b);
    }

    public static int computeLcmOfList(int[] numList) {
        int result = numList[0];
        for (int i = 1; i < numList.length; i++) {
            result = computeLcm(result, numList[i]);
        }
        return result;
    }

    public static int[] toIntegerEquation(Fraction[] fracs) {
        int[] dArr = new int[fracs.length];
        for (int i = 0; i < fracs.length; i++) {
            dArr[i] = fracs[i].mDenominator;
        }
        int lcm = computeLcmOfList(dArr);
        for (int i = 0; i < fracs.length; i++) {
            Fraction f = fracs[i];
            dArr[i] = (lcm / f.mDenominator) * f.mNumerator;
        }
        return dArr;
    }
}
