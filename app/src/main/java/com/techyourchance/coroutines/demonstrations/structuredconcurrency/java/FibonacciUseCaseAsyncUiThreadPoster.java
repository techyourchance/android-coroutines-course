package com.techyourchance.coroutines.demonstrations.structuredconcurrency.java;

import android.os.Handler;
import android.os.Looper;

import com.techyourchance.threadposter.BackgroundThreadPoster;
import com.techyourchance.threadposter.UiThreadPoster;

import java.math.BigInteger;

import androidx.annotation.WorkerThread;

class FibonacciUseCaseAsyncUiThreadPoster {


    public interface Callback {
        public void onFibonacciComputed(BigInteger result);
    }

    private final BackgroundThreadPoster mBackgroundThreadPoster;
    private final UiThreadPoster mUiThreadPoster;

    FibonacciUseCaseAsyncUiThreadPoster(BackgroundThreadPoster backgroundThreadPoster, UiThreadPoster uiThreadPoster) {
        mBackgroundThreadPoster = backgroundThreadPoster;
        mUiThreadPoster = uiThreadPoster;
    }

    public void computeFibonacci(int index, Callback callback) {
        mBackgroundThreadPoster.post(new Runnable() {
            @Override
            public void run() {
                BigInteger result = computeFibonacciBg(index);
                notifyResult(result, callback);
            }
        });
    }


    @WorkerThread
    private BigInteger computeFibonacciBg(int index) {
        if (index == 0) {
            return new BigInteger("0");
        } else if (index == 1) {
            return new BigInteger("1");
        } else {
            return computeFibonacciBg(index - 1).add(computeFibonacciBg(index - 2));
        }
    }

    private void notifyResult(BigInteger result, Callback callback) {
        mUiThreadPoster.post(new Runnable() {
            @Override
            public void run() {
                callback.onFibonacciComputed(result);
            }
        });
    }
}
