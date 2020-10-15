package com.techyourchance.coroutines.solutions.exercise6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.techyourchance.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.*

class Exercise6SolutionFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_6.description

    private lateinit var benchmarkUseCase: Exercise6SolutionBenchmarkUseCase

    private lateinit var btnStart: Button
    private lateinit var txtRemainingTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        benchmarkUseCase = compositionRoot.exercise6SolutionBenchmarkUseCase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_6, container, false)

        txtRemainingTime = view.findViewById(R.id.txt_remaining_time)

        btnStart = view.findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            logThreadInfo("button callback")

            val benchmarkDurationSeconds = 5

            coroutineScope.launch {
                updateRemainingTime(benchmarkDurationSeconds)
            }

            coroutineScope.launch {
                try {
                    btnStart.isEnabled = false
                    val iterationsCount = benchmarkUseCase.executeBenchmark(benchmarkDurationSeconds)
                    Toast.makeText(requireContext(), "$iterationsCount", Toast.LENGTH_SHORT).show()
                    btnStart.isEnabled = true
                } catch (e: CancellationException) {
                    btnStart.isEnabled = true
                    txtRemainingTime.text = "done!"
                    logThreadInfo("benchmark cancelled")
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


    private suspend fun updateRemainingTime(remainingTimeSeconds: Int) {
        for (time in remainingTimeSeconds downTo 0) {
            if (time > 0) {
                logThreadInfo("updateRemainingTime: $time seconds")
                txtRemainingTime.text = "$time seconds remaining"
                delay(1000)
            } else {
                txtRemainingTime.text = "done!"
            }
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return Exercise6SolutionFragment()
        }
    }
}