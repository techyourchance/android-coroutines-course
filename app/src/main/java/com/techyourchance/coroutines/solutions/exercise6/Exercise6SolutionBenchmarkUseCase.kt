package com.techyourchance.coroutines.solutions.exercise6

import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.techyourchance.coroutines.exercises.exercise6.PostBenchmarkResultsEndpoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class Exercise6SolutionBenchmarkUseCase(private val postBenchmarkResultsEndpoint: PostBenchmarkResultsEndpoint) {

    suspend fun executeBenchmark(benchmarkDurationSeconds: Int) = withContext(Dispatchers.Default) {
        logThreadInfo("benchmark started")

        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L

        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            ensureActive()
            iterationsCount++
        }

        logThreadInfo("benchmark completed")

        postBenchmarkResultsEndpoint.postBenchmarkResults(benchmarkDurationSeconds, iterationsCount)

        logThreadInfo("benchmark results posted to the server")

        iterationsCount
    }

}