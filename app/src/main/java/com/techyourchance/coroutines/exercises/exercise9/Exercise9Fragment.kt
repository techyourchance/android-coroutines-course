package com.techyourchance.coroutines.exercises.exercise9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.techyourchance.coroutines.exercises.exercise8.User
import com.techyourchance.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.*

class Exercise9Fragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_9.description

    private lateinit var fetchAndCacheUsersUseCase: FetchAndCacheUsersUseCaseExercise9

    private lateinit var btnFetch: Button
    private lateinit var txtElapsedTime: TextView
    private lateinit var txtUsers: TextView


    private val userIds = listOf<String>("bmq81", "gfn12", "gla34")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchAndCacheUsersUseCase = compositionRoot.fetchAndCacheUserUseCaseExercise9
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_9, container, false)

        view.apply {
            txtElapsedTime = findViewById(R.id.txt_elapsed_time)
            btnFetch = findViewById(R.id.btn_fetch_users)
            txtUsers = findViewById(R.id.txt_users)
        }

        btnFetch.setOnClickListener {
            logThreadInfo("button callback")

            val updateElapsedTimeJob = coroutineScope.launch {
                updateElapsedTime()
            }

            coroutineScope.launch {
                try {
                    btnFetch.isEnabled = false
                    fetchAndCacheUsersUseCase.fetchAndCacheUsers(userIds)
                    updateElapsedTimeJob.cancel()
                } catch (e: CancellationException) {
                    updateElapsedTimeJob.cancelAndJoin()
                    txtElapsedTime.text = ""
                    txtUsers.text = ""
                } finally {
                    btnFetch.isEnabled = true
                }
            }
        }

        return view
    }

    private fun bindUsers(users: List<User>) {
        txtUsers.text = users.joinToString("\n") { it.name }
    }

    override fun onStop() {
        logThreadInfo("onStop()")
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }


    private suspend fun updateElapsedTime() {
        val startTimeNano = System.nanoTime()
        while (true) {
            delay(100)
            val elapsedTimeNano = System.nanoTime() - startTimeNano
            val elapsedTimeMs = elapsedTimeNano / 1000000
            txtElapsedTime.text = "Elapsed time: $elapsedTimeMs ms"
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return Exercise9Fragment()
        }
    }
}