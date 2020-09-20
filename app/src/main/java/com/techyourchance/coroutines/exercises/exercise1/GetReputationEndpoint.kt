package com.techyourchance.coroutines.exercises.exercise1

import com.techyourchance.coroutines.common.ThreadInfoLogger
import kotlin.random.Random

class GetReputationEndpoint {
    fun getReputation(userId: String): Int {
        ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): called")
        Thread.sleep(3000)
        ThreadInfoLogger.logThreadInfo("GetReputationEndpoint#getReputation(): return data")
        return Random.nextInt(0, 100)
    }
}