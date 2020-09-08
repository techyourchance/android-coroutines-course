package com.techyourchance.coroutines

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.coroutines.common.ScreensNavigator
import com.techyourchance.coroutines.common.ToolbarDelegate
import com.techyourchance.coroutines.common.dependencyinjection.ActivityCompositionRoot

class MainActivity : AppCompatActivity(), ToolbarDelegate {

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var btnBack: ImageButton
    private lateinit var txtScreenTitle: TextView

    val compositionRoot by lazy {
        ActivityCompositionRoot(this,(application as MyApplication).applicationCompositionRoot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screensNavigator = compositionRoot.screensNavigator
        screensNavigator.init(savedInstanceState)

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener { screensNavigator.navigateUp() }

        txtScreenTitle = findViewById(R.id.txt_screen_title)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        screensNavigator.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (!screensNavigator.navigateBack()) {
            super.onBackPressed()
        }
    }

    override fun setScreenTitle(screenTitle: String) {
        txtScreenTitle.text = screenTitle
    }

    override fun showUpButton() {
        btnBack.visibility = View.VISIBLE
    }

    override fun hideUpButton() {
        btnBack.visibility = View.INVISIBLE
    }
}