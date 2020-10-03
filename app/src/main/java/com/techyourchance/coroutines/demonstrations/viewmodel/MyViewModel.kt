package com.techyourchance.coroutines.demonstrations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techyourchance.coroutines.common.ThreadInfoLogger
import kotlinx.coroutines.*

class MyViewModel: ViewModel() {
    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long> = _elapsedTime

    private val _isTrackingTime = MutableLiveData<Boolean>()
    val isTrackingTime: LiveData<Boolean> = _isTrackingTime

    fun toggleTrackElapsedTime() {
        val isTrackingTimeNow = isTrackingTime.value
        logThreadInfo("trackElapsedTime(); isTrackingTimeNow = $isTrackingTimeNow")
        if (isTrackingTimeNow == null || !isTrackingTimeNow) {
            startTracking()
        } else {
            stopTracking()
        }
    }

    private fun startTracking() = viewModelScope.launch {
        logThreadInfo("startTracking()")
        _isTrackingTime.postValue(true)

        val startTimeNano = System.nanoTime()

        while (true) {
            val elapsedTimeSeconds = (System.nanoTime() - startTimeNano) / 1_000_000_000L
            logThreadInfo("elapsed time: $elapsedTimeSeconds seconds")
            _elapsedTime.postValue(elapsedTimeSeconds)
            delay(1000)
        }
    }

    private fun stopTracking() {
        logThreadInfo("stopTracking()")
        _isTrackingTime.postValue(false)
        viewModelScope.coroutineContext.cancelChildren()
    }

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

}