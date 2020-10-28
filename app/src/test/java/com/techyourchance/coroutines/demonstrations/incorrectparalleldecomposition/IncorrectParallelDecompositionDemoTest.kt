package com.techyourchance.coroutines.demonstrations.incorrectparalleldecomposition

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.math.pow

class IncorrectParallelDecompositionDemoTest {

    @Test
    fun wrongUseOfCoroutines() = runBlocking {
        var totalIterations = 0
        withContext(Dispatchers.Default) {
            for (duration in 1..5) {
                launch {
                    val startTimeNano = System.nanoTime()
                    var iterations = 0
                    while (System.nanoTime() < startTimeNano + (duration * 10f.pow(9))) {
                        iterations++
                    }
                    totalIterations += iterations
                }
            }
        }
        println("total iterations: $totalIterations")
    }

    @Test
    fun atomicityProblemDemo() = runBlocking {
        var totalIterations = 0
        withContext(Dispatchers.Default) {
            repeat(100) {
                launch {
                    delay(100)
                    totalIterations++
                }
            }
        }
        println("total iterations: $totalIterations")
    }


}