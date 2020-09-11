package com.techyourchance.coroutines.common

import android.os.Bundle
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener
import com.techyourchance.coroutines.demonstrations.backgroundthread.BackgroundThreadDemoFragment
import com.techyourchance.coroutines.demonstrations.uithread.UiThreadDemoFragment
import com.techyourchance.coroutines.home.HomeFragment

class ScreensNavigator(private val fragNavController: FragNavController) {

    fun init(savedInstanceState: Bundle?) {
        fragNavController.rootFragmentListener = object : RootFragmentListener {
            override val numberOfRootFragments get() = 1

            override fun getRootFragment(index: Int) = HomeFragment.newInstance()
        }

        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    fun onSaveInstanceState(outState: Bundle?) {
        fragNavController.onSaveInstanceState(outState)
    }

    fun isRootScreen() = fragNavController.isRootFragment

    fun navigateBack(): Boolean {
        if (fragNavController.isRootFragment) {
            return false
        } else {
            fragNavController.popFragment()
            return true
        }
    }

    fun navigateUp() {
        fragNavController.popFragment()
    }

    fun toHomeScreen() {
        fragNavController.clearStack()
        fragNavController.pushFragment(HomeFragment.newInstance())
    }

    fun toUiThreadDemo() {
        fragNavController.pushFragment(UiThreadDemoFragment.newInstance())
    }

    fun toBackgroundThreadDemo() {
        fragNavController.pushFragment(BackgroundThreadDemoFragment.newInstance())
    }

}