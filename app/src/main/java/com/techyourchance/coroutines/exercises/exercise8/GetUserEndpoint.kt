package com.techyourchance.coroutines.exercises.exercise8

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GetUserEndpoint {

    suspend fun getUser(userId: String): User = withContext(Dispatchers.IO) {
        delay(500)
        return@withContext User(userId, "user ${userId}")
    }
}