package com.techyourchance.coroutines.demonstrations.noncancellable

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.techyourchance.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.*

class NonCancellableDemoFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_6.description

    private lateinit var makeCustomerPremiumUseCase: MakeCustomerPremiumUseCase

    private lateinit var edtCustomerId: EditText
    private lateinit var btnMakePremium: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeCustomerPremiumUseCase = compositionRoot.makeCustomerPremiumUseCase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_premium_customer, container, false)

        view.apply {
            edtCustomerId = findViewById(R.id.edt_customer_id)
            btnMakePremium = findViewById(R.id.btn_make_premium)
        }

        edtCustomerId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnMakePremium.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnMakePremium.setOnClickListener {
            logThreadInfo("button callback")

            val benchmarkDurationSeconds = 5

            coroutineScope.launch {
                try {
                    disableUserInput()
                    makeCustomerPremiumUseCase.makeCustomerPremium(edtCustomerId.text.toString())
                    enableUserInput()
                    Toast.makeText(requireContext(), "the user became premium", Toast.LENGTH_SHORT).show()
                } catch (e: CancellationException) {
                    enableUserInput()
                    logThreadInfo("flow cancelled")
                }
            }
        }

        return view
    }

    private fun enableUserInput() {
        edtCustomerId.isEnabled = true
        btnMakePremium.isEnabled = true
    }

    private fun disableUserInput() {
        edtCustomerId.isEnabled = false
        btnMakePremium.isEnabled = false
    }

    override fun onStop() {
        logThreadInfo("onStop()")
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    companion object {
        fun newInstance(): Fragment {
            return NonCancellableDemoFragment()
        }
    }
}