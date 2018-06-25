package com.manminh.simplechem.search;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor {
    private static final int NUMBER_AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private java.util.concurrent.ThreadPoolExecutor mThreadPoolExecutor;

    private static final MyThreadPoolExecutor instance = new MyThreadPoolExecutor();

    public static MyThreadPoolExecutor getInstance() {
        return instance;
    }

    private MyThreadPoolExecutor() {
        BlockingDeque<Runnable> mThreadQueue = new LinkedBlockingDeque<>();
        mThreadPoolExecutor = new java.util.concurrent.ThreadPoolExecutor(
                NUMBER_AVAILABLE_CORES,
                NUMBER_AVAILABLE_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mThreadQueue
        );
    }

    public void execute(Runnable thread) {
        mThreadPoolExecutor.execute(thread);
    }
}
