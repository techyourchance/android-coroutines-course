package com.techyourchance.coroutines.exercises.exercise5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment
import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Exercise5Fragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_5.description

    private lateinit var edtUserId: EditText
    private lateinit var btnGetReputation: Button
    private lateinit var txtElapsedTime: TextView

    private var job: Job? = null

    private val getReputationUseCase by lazy {
        compositionRoot.getMyReputationUseCase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_5, container, false)

        txtElapsedTime = view.findViewById(R.id.txt_elapsed_time)

        edtUserId = view.findViewById(R.id.edt_user_id)
        edtUserId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnGetReputation.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnGetReputation = view.findViewById(R.id.btn_get_reputation)
        btnGetReputation.setOnClickListener {
            logThreadInfo("button callback")
            job = coroutineScope.launch {
                btnGetReputation.isEnabled = false
                val reputation = getReputationUseCase(edtUserId.text.toString())
                Toast.makeText(requireContext(), "reputation: $reputation", Toast.LENGTH_SHORT).show()
                btnGetReputation.isEnabled = true
            }
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
        btnGetReputation.isEnabled = true
    }

    private fun logThreadInfo(message: String) {
        ThreadInfoLogger.logThreadInfo(message)
    }

    companion object {
        fun newInstance(): Fragment {
            return Exercise5Fragment()
        }
    }
}