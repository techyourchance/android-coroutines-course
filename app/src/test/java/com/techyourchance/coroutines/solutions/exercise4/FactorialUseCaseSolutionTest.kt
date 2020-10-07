package com.techyourchance.coroutines.solutions.exercise4

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigInteger

class FactorialUseCaseSolutionTest {

    private lateinit var SUT: FactorialUseCaseSolution

    @Before
    fun setup() {
        SUT = FactorialUseCaseSolution()
    }

    @Test
    fun computeFactorial_0_returns1() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFactorial(0, 1000)
            // Assert
            assertThat((result as FactorialUseCaseSolution.Result.Success).result, `is`(BigInteger("1")))
        }
    }

    @Test
    fun computeFactorial_1_returns1() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFactorial(1, 1000)
            // Assert
            assertThat((result as FactorialUseCaseSolution.Result.Success).result, `is`(BigInteger("1")))
        }
    }

    @Test
    fun computeFactorial_10_returnsCorrectAnswer() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFactorial(10, 1000)
            // Assert
            assertThat((result as FactorialUseCaseSolution.Result.Success).result, `is`(BigInteger("3628800")))
        }
    }

    @Test
    fun computeFactorial_30_returnsCorrectAnswer() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFactorial(30, 1000)
            // Assert
            assertThat((result as FactorialUseCaseSolution.Result.Success).result, `is`(BigInteger("265252859812191058636308480000000")))
        }
    }


}