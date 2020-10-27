package com.techyourchance.coroutines.exercises.exercise8

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UsersDao {

    suspend fun upsertUserInfo(user: User) = withContext(Dispatchers.IO) {
        delay(500)
    }
}