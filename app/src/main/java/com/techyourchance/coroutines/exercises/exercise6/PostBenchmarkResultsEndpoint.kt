package com.techyourchance.coroutines.exercises.exercise6

import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Existing class which you can't change
 */
class PostBenchmarkResultsEndpoint {
    fun postBenchmarkResults(timeSeconds: Int, iterations: Long) {
        Thread.sleep(500)
    }
}