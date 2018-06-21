package com.manminh.simplechem.balance.exception;

public class ParseFormulaException extends Exception {
    private static String EXCEPTION_MSG = "Parse formula exception";

    public static final int EMPTY_STRING = 0;
    public static final int INVALID_ELEMENT = 1;
    public static final int INVALID_CHARACTER = 2;
    public static final int INVALID_PARENTHESES = 3;

    private int mExceptionCode = -1;

    public ParseFormulaException(int code) {
        super(EXCEPTION_MSG + ". Code: " + String.valueOf(code));
        mExceptionCode = code;
    }

    public ParseFormulaException(int code, Throwable cause) {
        super(EXCEPTION_MSG + ". Code: " + String.valueOf(code), cause);
        mExceptionCode = code;
    }

    public int getExceptionCode() {
        return mExceptionCode;
    }
}
