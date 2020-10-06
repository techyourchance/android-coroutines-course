package com.techyourchance.coroutines.exercises.exercise4

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import java.math.BigInteger
import androidx.fragment.app.Fragment
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment
import com.techyourchance.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Exercise4Fragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_4.description

    private lateinit var edtArgument: EditText
    private lateinit var edtTimeout: EditText
    private lateinit var btnStartWork: Button
    private lateinit var txtResult: TextView

    private lateinit var factorialUseCase: FactorialUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factorialUseCase = compositionRoot.factorialUseCase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_4, container, false)

        view.apply {
            edtArgument = findViewById(R.id.edt_argument)
            edtTimeout = findViewById(R.id.edt_timeout)
            btnStartWork = findViewById(R.id.btn_compute)
            txtResult = findViewById(R.id.txt_result)
        }

        edtArgument.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnStartWork.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnStartWork.setOnClickListener {
            txtResult.text = ""
            btnStartWork.isEnabled = false

            val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(btnStartWork.windowToken, 0)

            val argument = Integer.valueOf(edtArgument.text.toString())

            coroutineScope.launch {
                val result = factorialUseCase.computeFactorial(argument, getTimeout())
                when (result) {
                    is FactorialUseCase.Result.Success -> onFactorialComputed(result.result)
                    is FactorialUseCase.Result.Timeout -> onFactorialComputationTimedOut()
                }
            }
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.cancel()
    }

    private fun onFactorialComputed(result: BigInteger) {
        txtResult.text = result.toString()
        btnStartWork.isEnabled = true
    }

    private fun onFactorialComputationTimedOut() {
        txtResult.text = "Computation timed out"
        btnStartWork.isEnabled = true
    }

    private fun getTimeout() : Int {
        var timeout: Int
        if (edtTimeout.text.toString().isEmpty()) {
            timeout = MAX_TIMEOUT_MS
        } else {
            timeout = Integer.valueOf(edtTimeout.text.toString())
            if (timeout > MAX_TIMEOUT_MS) {
                timeout = MAX_TIMEOUT_MS
            }
        }
        return timeout
    }

    companion object {
        fun newInstance(): Fragment {
            return Exercise4Fragment()
        }
        private const val MAX_TIMEOUT_MS = 3000
    }
}
