package com.techyourchance.coroutines.demonstrations.structuredconcurrency.java;

import android.os.Handler;
import android.os.Looper;

import java.math.BigInteger;

import androidx.annotation.WorkerThread;

class FibonacciUseCaseAsyncUi {

    public interface Callback {
        public void onFibonacciComputed(BigInteger result);
    }

    public void computeFibonacci(int index, Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BigInteger result = computeFibonacciBg(index);
                notifyResult(result, callback);
            }
        }).start();
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
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callback.onFibonacciComputed(result);
            }
        });
    }
}
