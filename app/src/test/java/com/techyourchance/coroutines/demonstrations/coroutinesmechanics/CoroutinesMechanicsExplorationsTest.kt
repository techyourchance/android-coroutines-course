package com.techyourchance.coroutines.demonstrations.coroutinesmechanics

import com.techyourchance.coroutines.common.TestUtils
import com.techyourchance.coroutines.common.TestUtils.printCoroutineScopeInfo
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
}