package com.jiujiu.mytodoapp.Utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private Executor mDiskExecutor;
    private Executor mNetWorkExecutor;
    private Executor mMainThreadExecutorr;

    public AppExecutor(Executor mDiskExecutor, Executor mNetWorkExecutor, Executor mMainThreadExecutorr) {
        this.mDiskExecutor = mDiskExecutor;
        this.mNetWorkExecutor = mNetWorkExecutor;
        this.mMainThreadExecutorr = mMainThreadExecutorr;
    }

    public AppExecutor() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
    }

    private static class MainThreadExecutor implements Executor {
        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            handler.post(command);
        }
    }

    public Executor diskExecutor() {
        return mDiskExecutor;
    }

    public Executor netWorkExecutor() {
        return mNetWorkExecutor;
    }

    public Executor mainThreadExecutorr() {
        return mMainThreadExecutorr;
    }
}
