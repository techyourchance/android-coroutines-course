package com.techyourchance.coroutines.solutions.exercise8

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
import com.techyourchance.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.*

class Exercise8SolutionFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_8.description

    private lateinit var fetchAndCacheUsersUseCase: Exercise8SolutionFetchAndCacheUsersUseCase

    private lateinit var btnFetch: Button
    private lateinit var txtElapsedTime: TextView

    private val userIds = listOf<String>("bmq81", "gfn12", "gla34")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchAndCacheUsersUseCase = compositionRoot.exercise8SolutionFetchAndCacheUserUseCase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_8, container, false)

        view.apply {
            txtElapsedTime = findViewById(R.id.txt_elapsed_time)
            btnFetch = findViewById(R.id.btn_fetch_users)
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
                    withContext(NonCancellable) {
                        updateElapsedTimeJob.cancelAndJoin()
                        txtElapsedTime.text = ""
                    }
                } finally {
                    withContext(NonCancellable) {
                        btnFetch.isEnabled = true
                    }
                }
            }
        }

        return view
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
            return Exercise8SolutionFragment()
        }
    }
}