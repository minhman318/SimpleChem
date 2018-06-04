package com.manminh.simplechem.exception;

public class FailedBalanceException extends Exception {
    private static String EXCEPTION_MSG = "Cannot balance exception";

    public static final int NO_NEED_BALANCE = 0;
    public static final int ELEMENT_NOT_EQUAL = 1;
    public static final int CANNOT_BALANCE = 2;

    private int mExceptionCode = -1;

    public FailedBalanceException(int code) {
        super(EXCEPTION_MSG + ". Code: " + String.valueOf(code));
        mExceptionCode = code;
    }

    public FailedBalanceException(int code, Throwable cause) {
        super(EXCEPTION_MSG + ". Code: " + String.valueOf(code), cause);
        mExceptionCode = code;
    }

    public int getExceptionCode() {
        return mExceptionCode;
    }
}
