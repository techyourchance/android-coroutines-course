package com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative

import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class CancellableBenchmarkUseCase {

    suspend fun executeBenchmark(benchmarkDurationSeconds: Int) = withContext(Dispatchers.Default) {
        logThreadInfo("benchmark started")

        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L

        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano && isActive) {
            iterationsCount++
        }

        logThreadInfo("benchmark completed")

        iterationsCount
    }

}