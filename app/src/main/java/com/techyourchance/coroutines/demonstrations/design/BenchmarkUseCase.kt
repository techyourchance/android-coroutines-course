package com.techyourchance.coroutines.demonstrations.design

import com.techyourchance.coroutines.common.ThreadInfoLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BenchmarkUseCase {

    suspend fun executeBenchmark(benchmarkDurationSeconds: Int) = withContext(Dispatchers.Default) {
        logThreadInfo("benchmark started")

        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L

        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }

        logThreadInfo("benchmark completed")

        iterationsCount
    }


    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

}