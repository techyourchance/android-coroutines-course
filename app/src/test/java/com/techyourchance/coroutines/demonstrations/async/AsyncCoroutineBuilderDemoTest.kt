package com.techyourchance.coroutines.demonstrations.async

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.math.pow

class AsyncCoroutineBuilderDemoTest {

    @Test
    fun correctUseOfCoroutinesVariant1() = runBlocking {
        withContext(Dispatchers.Default) {
            val deferreds = mutableListOf<Deferred<Int>>()

            for (duration in 1..5) {
                deferreds.add(
                        async {
                            val startTimeNano = System.nanoTime()
                            var iterations = 0
                            while (System.nanoTime() < startTimeNano + (duration * 10f.pow(9))) {
                                iterations++
                            }
                            iterations
                        }
                )
            }
            
            var totalIterations = 0
            for (deferred in deferreds) {
                totalIterations += deferred.await()
            }

            println("total iterations: $totalIterations")
        }
    }

    @Test
    fun correctUseOfCoroutinesVariant2() = runBlocking {
        withContext(Dispatchers.Default) {
            val totalIterations = (1..5).toList().map { duration ->
                async {
                    val startTimeNano = System.nanoTime()
                    var iterations = 0
                    while (System.nanoTime() < startTimeNano + (duration * 10f.pow(9))) {
                        iterations++
                    }
                    iterations
                }
            }.awaitAll().sum()

            println("total iterations: $totalIterations")
        }
    }
}