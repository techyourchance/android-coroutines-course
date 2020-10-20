package com.techyourchance.coroutines.demonstrations.coroutinesmechanics

import com.techyourchance.coroutines.common.TestUtils
import com.techyourchance.coroutines.common.TestUtils.printCoroutineScopeInfo
import com.techyourchance.coroutines.common.TestUtils.printJobsHierarchy
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.Exception
import kotlin.coroutines.EmptyCoroutineContext

class CoroutinesMechanicsExplorationsTest {

    @Test
    fun coroutineScope_launch() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob)
            scope.printCoroutineScopeInfo()
            val job = scope.launch {
                this.printCoroutineScopeInfo()
                delay(100)
                println("coroutine done")
            }
            job.join()
            println("test done")
        }
    }

    @Test
    fun coroutineScope_launchAndChangeContext() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
            scope.printCoroutineScopeInfo()
            val job = scope.launch(CoroutineName("my coroutine") + Dispatchers.Default) {
                this.printCoroutineScopeInfo()
                delay(100)
                println("coroutine done")
            }
            job.join()
            println("test done")
        }
    }

    @Test
    fun coroutineScope_withContext() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
            scope.printCoroutineScopeInfo()
            val job = scope.launch(CoroutineName("my coroutine") + Dispatchers.Default) {
                this.printCoroutineScopeInfo()
                delay(100)
                withContext(CoroutineName("withContext") + Dispatchers.IO) {
                    this.printCoroutineScopeInfo()
                    delay(100)
                    println("withContext done")
                }
                println("coroutine done")
            }
            job.join()
            println("test done")
        }
    }

    @Test
    fun coroutineScope_jobsHierarchy() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
            scope.printCoroutineScopeInfo()
            val job = scope.launch(CoroutineName("my coroutine") + Dispatchers.Default) {
                this.printCoroutineScopeInfo()
                delay(100)
                withContext(CoroutineName("withContext") + Dispatchers.IO) {
                    this.printCoroutineScopeInfo()
                    delay(100)
                    printJobsHierarchy(scopeJob)
                    println("withContext done")
                }
                println("coroutine done")
            }
            scope.launch(CoroutineName("my additional coroutine")) {
                delay(500)
            }
            job.join()
            println("test done")
        }
    }

    @Test
    fun coroutinesCancellationMechanics() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
            val job = scope.launch(CoroutineName("my coroutine") + Dispatchers.Default) {
                try {
                    delay(100)
                    withContext(CoroutineName("withContext") + Dispatchers.IO) {
                        try {
                            printJobsHierarchy(scopeJob)
                            delay(100)
                            println("withContext done")
                        } catch (e: CancellationException) {
                            println("withContext cancelled")
                        }
                    }
                    println("coroutine done")
                } catch (e: CancellationException) {
                    println("coroutine cancelled")
                }
            }
            scope.launch(CoroutineName("my additional coroutine")) {
                delay(150)
                scopeJob.cancel()
            }
            job.join()
            println("test done")
        }
    }


    @Test
    fun coroutinesCancellationMechanicsNonCancellable() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
            val job = scope.launch(CoroutineName("my coroutine") + Dispatchers.Default) {
                try {
                    delay(100)
                    withContext(NonCancellable + CoroutineName("withContext") + Dispatchers.IO) {
                        try {
                            printJobsHierarchy(scopeJob)
                            delay(100)
                            println("withContext done")
                        } catch (e: CancellationException) {
                            println("withContext cancelled")
                        }
                    }
                    println("coroutine done")
                } catch (e: CancellationException) {
                    println("coroutine cancelled")
                }
            }
            scope.launch(CoroutineName("my additional coroutine")) {
                delay(150)
                scopeJob.cancel()
            }
            job.join()
            println("test done")
        }
    }


}