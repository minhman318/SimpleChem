package com.manminh.simplechem.exception;

public class ParseEquationException extends Exception {
    private static String EXCEPTION_MSG = "Parse equation exception";

    public static final int INVALID_SYNTAX = 0;
    public static final int INVALID_FORMULA = 1;

    private int mExceptionCode = -1;

    public ParseEquationException(int code) {
        super(EXCEPTION_MSG + ". Code: " + String.valueOf(code));
        mExceptionCode = code;
    }

    public ParseEquationException(int code, Throwable cause) {
        super(EXCEPTION_MSG + ". Code: " + String.valueOf(code), cause);
        mExceptionCode = code;
    }

    public int getExceptionCode() {
        return mExceptionCode;
    }
}
