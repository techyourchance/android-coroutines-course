package com.techyourchance.coroutines.exercises.exercise5

import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.techyourchance.coroutines.exercises.exercise1.GetReputationEndpoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMyReputationUseCase(val getReputationEndpoint: GetReputationEndpoint) {
    suspend operator fun invoke(userId: String) = withContext(Dispatchers.Default) {
        logThreadInfo("getReputationForUser()")
        getReputationEndpoint.getReputation(userId)
    }
}
