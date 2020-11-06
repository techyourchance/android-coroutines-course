package com.techyourchance.coroutines.demonstrations.coroutineexceptionhandler

import com.techyourchance.coroutines.common.TestUtils
import com.techyourchance.coroutines.common.TestUtils.printCoroutineScopeInfo
import com.techyourchance.coroutines.common.TestUtils.printJobsHierarchy
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CoroutineExceptionHandlerDemoTest {

    @Test
    fun uncaughtException() {
        runBlocking {
            val scope = CoroutineScope(Dispatchers.Default)
            val job = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            job.join()
        }
        Thread.sleep(100)
        println("test completed")
    }

    @Test
    fun caughtException() {
        runBlocking {

            val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
                println("Caught exception: $throwable")
            }

            val scope = CoroutineScope(Dispatchers.Default + coroutineExceptionHandler)
            val job = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            job.join()
        }
        Thread.sleep(100)
        println("test completed")
    }

    @Test
    fun caughtExceptionAtCoroutineLevel() {
        runBlocking {

            val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
                println("Caught exception: $throwable")
            }

            val scope = CoroutineScope(Dispatchers.Default)
            val job = scope.launch(coroutineExceptionHandler) {
                delay(50)
                throw RuntimeException()
            }
            job.join()
        }
        Thread.sleep(100)
        println("test completed")
    }




}