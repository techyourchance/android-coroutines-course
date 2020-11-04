package com.techyourchance.coroutines.demonstrations.uncaughtexception

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class LoginEndpointUncaughtException {

    class RequestTimeoutException(msg: String) : RuntimeException(msg) {}

    sealed class Response {
        data class Success(val user: LoggedInUser): Response()
        data class Failure(val statusCode: Int): Response()
    }

    private var attemptsCounter = AtomicInteger(0)

    suspend fun logIn(username: String, password: String): Response = withContext(Dispatchers.IO) {
        delay(1000)
        val attempt = attemptsCounter.incrementAndGet().rem(3)
        if (attempt == 0) {
            throw RequestTimeoutException("timed out")
        }
        if (attempt == 1) {
            return@withContext Response.Success(
                    LoggedInUser(
                            UUID.randomUUID().toString(),
                            username,
                            "authToken"
                    )
            )
        } else {
            return@withContext Response.Failure(401)
        }
    }
}