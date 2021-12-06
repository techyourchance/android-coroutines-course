package com.techyourchance.coroutines.exercises.exercise1

import com.techyourchance.coroutines.common.ThreadInfoLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class GetReputationEndpoint {
    suspend fun getReputation(userId: String) = withContext(Dispatchers.Default) {
        ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): called")
        Thread.sleep(3000)
        ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): return data")
        Random.nextInt(0, 100)
    }
}