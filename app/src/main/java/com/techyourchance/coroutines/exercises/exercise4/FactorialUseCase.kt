package com.techyourchance.coroutines.exercises.exercise4

import kotlinx.coroutines.*
import java.math.BigInteger
import kotlin.math.ceil

class FactorialUseCase {

    sealed class Result {
        class Success(val result: BigInteger) : Result()
        object Timeout : Result()
    }

    suspend fun computeFactorial(argument: Int, timeout: Int): Result = withContext(Dispatchers.IO) {
        try {
            withTimeout(timeMillis = timeout.toLong()) {
                val computationRanges = getComputationRanges(argument)

                val partialProductsForRanges = computePartialProducts(computationRanges)

                val result = computeFinalResult(partialProductsForRanges)

                Result.Success(result)
            }
        } catch (e: TimeoutCancellationException) {
            Result.Timeout
        }

    }

    private fun getComputationRanges(factorialArgument: Int): Array<ComputationRange> {
        val numberOfThreads = getNumberOfThreads(factorialArgument)
        val threadsComputationRanges = Array(numberOfThreads) { ComputationRange(0, 0) }

        val ranges = (1..factorialArgument).chunked(factorialArgument / numberOfThreads + 1)
        repeat(numberOfThreads) { index ->
            val (rangeStart, rangeEnd) = if (ranges.isNotEmpty()) {
                ranges[index].first().toLong() to ranges[index].last().toLong()
            } else {
                1L to 1L
            }
            threadsComputationRanges[index] = ComputationRange(rangeStart, rangeEnd)
        }

        return threadsComputationRanges.reversedArray()
    }

    private fun getNumberOfThreads(factorialArgument: Int): Int {
        return if (factorialArgument < 20)
            1
        else
            Runtime.getRuntime().availableProcessors()
    }

    private suspend fun computePartialProducts(computationRanges: Array<ComputationRange>): List<BigInteger> = coroutineScope {
        return@coroutineScope withContext(Dispatchers.IO) {
            return@withContext computationRanges.map {
                computeProductForRangeAsync(it)
            }.awaitAll()
        }
    }

    private fun CoroutineScope.computeProductForRangeAsync(computationRange: ComputationRange): Deferred<BigInteger> = async(Dispatchers.IO) {
        val rangeStart = computationRange.start
        val rangeEnd = computationRange.end

        var product = BigInteger("1")
        for (num in rangeStart..rangeEnd) {
            if (!isActive) {
                break
            }
            product = product.multiply(BigInteger(num.toString()))
        }

        return@async product
    }

    private suspend fun computeFinalResult(partialProducts: List<BigInteger>): BigInteger = withContext(Dispatchers.IO) {
        var result = BigInteger("1")
        for (partialProduct in partialProducts) {
            if (!isActive) {
                break
            }
            result = result.multiply(partialProduct)
        }
        return@withContext result
    }

    private data class ComputationRange(val start: Long, val end: Long)
}
