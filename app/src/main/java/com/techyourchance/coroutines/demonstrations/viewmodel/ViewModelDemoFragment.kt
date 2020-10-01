package com.techyourchance.coroutines.demonstrations.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment
import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.home.ScreenReachableFromHome

class ViewModelDemoFragment : BaseFragment() {

    override val screenTitle get() = ScreenReachableFromHome.VIEWMODEL_DEMO.description

    private lateinit var btnTrackTime: Button
    private lateinit var txtElapsedTime: TextView

    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel = ViewModelProvider(this, MyViewModelFactory()).get(MyViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_viewmodel_demo, container, false)

        txtElapsedTime = view.findViewById(R.id.txt_elapsed_time)
        btnTrackTime = view.findViewById(R.id.btn_track_time)

        btnTrackTime.setOnClickListener {
            logThreadInfo("button callback")
            myViewModel.toggleTrackElapsedTime()
        }

        myViewModel.elapsedTime.observe(viewLifecycleOwner, Observer { elapsedTime ->
            txtElapsedTime.text = elapsedTime.toString()
        })

        myViewModel.isTrackingTime.observe(viewLifecycleOwner, Observer { isTrackingTime ->
            btnTrackTime.text = if (isTrackingTime) { "Stop tracking" } else { "Start tracking" }
        })

        return view
    }

    override fun onStop() {
        logThreadInfo("onStop()")
        super.onStop()
    }

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return ViewModelDemoFragment()
        }
    }
}