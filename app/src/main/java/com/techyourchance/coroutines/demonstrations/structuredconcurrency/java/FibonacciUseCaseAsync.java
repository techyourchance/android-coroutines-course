package com.techyourchance.coroutines.demonstrations.structuredconcurrency.java;

import java.math.BigInteger;

import androidx.annotation.WorkerThread;

class FibonacciUseCaseAsync {

    public interface Callback {
        public void onFibonacciComputed(BigInteger result);
    }

    public void computeFibonacci(int index, Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BigInteger result = computeFibonacciBg(index);
                callback.onFibonacciComputed(result);
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
}
