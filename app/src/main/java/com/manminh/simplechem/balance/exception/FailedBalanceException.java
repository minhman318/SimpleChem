package com.manminh.simplechem.balance.exception;


public class FailedBalanceException extends Exception {
    private static String EXCEPTION_MSG = "Cannot balance exception";

    public static final int HAS_BEEN_BALANCED = 0;
    public static final int BALANCE_FAILED = 1;

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
