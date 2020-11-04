package com.techyourchance.coroutines.demonstrations.uncaughtexception

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCaseUncaughtException(
        private val loginEndpointUncaughtException: LoginEndpointUncaughtException,
        private val userStateManager: UserStateManager
) {

    sealed class Result {
        data class Success(val user: LoggedInUser): Result()
        object InvalidCredentials: Result()
        object GeneralError: Result()
    }

    suspend fun logIn(username: String, password: String): Result = withContext(Dispatchers.IO) {

        val response = loginEndpointUncaughtException.logIn(username, password)

        return@withContext when(response) {
            is LoginEndpointUncaughtException.Response.Success -> {
                userStateManager.userLoggedIn(response.user)
                Result.Success(response.user)
            }
            is LoginEndpointUncaughtException.Response.Failure ->
                when(response.statusCode) {
                    401 -> Result.InvalidCredentials
                    else -> Result.GeneralError
                }
        }

    }
}
