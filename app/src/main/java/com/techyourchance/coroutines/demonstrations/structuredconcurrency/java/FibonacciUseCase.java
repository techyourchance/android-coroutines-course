package com.techyourchance.coroutines.demonstrations.structuredconcurrency.java;

import java.math.BigInteger;

class FibonacciUseCase {

    public BigInteger computeFibonacci(int index) {
        if (index == 0) {
            return new BigInteger("0");
        } else if (index == 1) {
            return new BigInteger("1");
        } else {
            return computeFibonacci(index - 1).add(computeFibonacci(index - 2));
        }
    }
}
