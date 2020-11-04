package com.techyourchance.coroutines.demonstrations.uncaughtexception

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UserStateManager {

    suspend fun userLoggedIn(user: LoggedInUser) = withContext(Dispatchers.IO) {
        delay(1000)
        println("new logged in user: $user")
    }

    suspend fun userLoggedOut() = withContext(Dispatchers.IO) {
        delay(1000)
        println("logged out")
    }
}