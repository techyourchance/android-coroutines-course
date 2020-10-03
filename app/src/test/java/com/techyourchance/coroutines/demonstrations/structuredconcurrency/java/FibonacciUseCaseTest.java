package com.techyourchance.coroutines.demonstrations.structuredconcurrency.java;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class FibonacciUseCaseTest {

    FibonacciUseCase SUT;

    @Before
    public void setup() throws Exception {
        SUT = new FibonacciUseCase();
    }

    @Test
    public void computeFibonacci_0_returns0() throws Exception {
        // Arrange
        // Act
        BigInteger result = SUT.computeFibonacci(0);
        // Assert
        assertThat(result, is(new BigInteger("0")));
    }

    @Test
    public void computeFibonacci_1_returns1() throws Exception {
        // Arrange
        // Act
        BigInteger result = SUT.computeFibonacci(1);
        // Assert
        assertThat(result, is(new BigInteger("1")));
    }

    @Test
    public void computeFibonacci_10_returnsCorrectAnswer() throws Exception {
        // Arrange
        // Act
        BigInteger result = SUT.computeFibonacci(10);
        // Assert
        assertThat(result, is(new BigInteger("55")));
    }

    @Test
    public void computeFibonacci_30_returnsCorrectAnswer() throws Exception {
        // Arrange
        // Act
        BigInteger result = SUT.computeFibonacci(30);
        // Assert
        assertThat(result, is(new BigInteger("832040")));
    }

}