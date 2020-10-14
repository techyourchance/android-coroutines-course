package com.techyourchance.coroutines.solutions.exercise5

import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.techyourchance.coroutines.exercises.exercise1.GetReputationEndpoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetReputationUseCase(private val getReputationEndpoint: GetReputationEndpoint) {

    suspend fun getReputationForUser(userId: String): Int {
        return withContext(Dispatchers.Default) {
            logThreadInfo("getReputationForUser()")
            getReputationEndpoint.getReputation(userId)
        }
    }

}