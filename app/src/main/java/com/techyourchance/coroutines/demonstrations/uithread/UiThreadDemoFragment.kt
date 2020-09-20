package com.techyourchance.coroutines.demonstrations.uithread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment
import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.home.ScreenReachableFromHome

class UiThreadDemoFragment : BaseFragment() {

    override val screenTitle get() = ScreenReachableFromHome.UI_THREAD_DEMO.description

    private lateinit var btnStart: Button
    private lateinit var txtRemainingTime: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_loop_iterations_demo, container, false)

        txtRemainingTime = view.findViewById(R.id.txt_remaining_time)

        btnStart = view.findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            logThreadInfo("button callback")
            btnStart.isEnabled = false
            executeBenchmark()
            btnStart.isEnabled = true
        }

        return view
    }

    private fun executeBenchmark() {
        val benchmarkDurationSeconds = 5

        updateRemainingTime(benchmarkDurationSeconds)

        logThreadInfo("benchmark started")

        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L

        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }

        logThreadInfo("benchmark completed")

        Toast.makeText(requireContext(), "$iterationsCount", Toast.LENGTH_SHORT).show()
    }

    private fun updateRemainingTime(remainingTimeSeconds: Int) {
        logThreadInfo("updateRemainingTime: $remainingTimeSeconds seconds")

        if (remainingTimeSeconds > 0) {
            txtRemainingTime.text = "$remainingTimeSeconds seconds remaining"
            Handler(Looper.getMainLooper()).postDelayed({
                updateRemainingTime(remainingTimeSeconds - 1)
            }, 1000)
        } else {
            txtRemainingTime.text = "done!"
        }

    }

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return UiThreadDemoFragment()
        }
    }
}