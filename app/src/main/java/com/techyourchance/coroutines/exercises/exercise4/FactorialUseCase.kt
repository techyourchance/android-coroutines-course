package com.techyourchance.coroutines.exercises.exercise4

import java.math.BigInteger

import androidx.annotation.WorkerThread
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class FactorialUseCase {

    public sealed class Result {
        class Success(val result: BigInteger) : Result()
        object Timeout : Result()
    }

    suspend fun computeFactorial(argument: Int, timeout: Int) : Result = withContext(Dispatchers.IO) {
        try {
            withTimeout(timeMillis = timeout.toLong()) {
                val computationRanges = getComputationRanges(argument)

                val partialProductsForRanges = computePartialProducts(computationRanges)

                val result = computeFinalResult(partialProductsForRanges)

                Result.Success(result)
            }
        } catch (e : TimeoutCancellationException) {
            Result.Timeout
        }

    }

    private fun getComputationRanges(factorialArgument: Int) : Array<ComputationRange> {
        val numberOfThreads = getNumberOfThreads(factorialArgument)

        val threadsComputationRanges = Array(numberOfThreads) { ComputationRange(0, 0) }

        val computationRangeSize = factorialArgument / numberOfThreads

        var nextComputationRangeEnd = factorialArgument.toLong()

        for (i in numberOfThreads - 1 downTo 0) {
            threadsComputationRanges[i] = ComputationRange(
                    nextComputationRangeEnd - computationRangeSize + 1,
                    nextComputationRangeEnd
            )
            nextComputationRangeEnd = threadsComputationRanges[i].start - 1
        }

        // add potentially "remaining" values to first thread's range
        //threadsComputationRanges[0] = ComputationRange(1, threadsComputationRanges[0].end)

        return threadsComputationRanges
    }

    private fun getNumberOfThreads(factorialArgument: Int): Int {
        return if (factorialArgument < 20)
            1
        else
            Runtime.getRuntime().availableProcessors()
    }

    private suspend fun computePartialProducts(computationRanges: Array<ComputationRange>) : List<BigInteger> = coroutineScope {
        return@coroutineScope withContext(Dispatchers.IO) {
            return@withContext computationRanges.map {
                computeProductForRangeAsync(it)
            }.awaitAll()
        }
    }

    private fun CoroutineScope.computeProductForRangeAsync(computationRange: ComputationRange) : Deferred<BigInteger> = async(Dispatchers.IO) {
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
