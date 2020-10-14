package com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative2

import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class BlockingBenchmarkUseCase {

    suspend fun executeBenchmark(benchmarkDurationSeconds: Int): Long {
        logThreadInfo("benchmark started")

        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L

        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            coroutineContext.ensureActive()
            iterationsCount++
        }

        logThreadInfo("benchmark completed")

        return iterationsCount
    }

}