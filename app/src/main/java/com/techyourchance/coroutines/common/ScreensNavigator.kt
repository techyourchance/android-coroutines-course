package com.techyourchance.coroutines.common

import android.os.Bundle
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener
import com.techyourchance.coroutines.demonstrations.backgroundthread.BackgroundThreadDemoFragment
import com.techyourchance.coroutines.demonstrations.basiccoroutines.BasicCoroutinesDemoFragment
import com.techyourchance.coroutines.demonstrations.concurrentcoroutines.ConcurrentCoroutinesDemoFragment
import com.techyourchance.coroutines.demonstrations.coroutinescancellation.CoroutinesCancellationDemoFragment
import com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative.CoroutinesCancellationCooperativeDemoFragment
import com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative2.CoroutinesCancellationCooperative2DemoFragment
import com.techyourchance.coroutines.demonstrations.design.DesignDemoFragment
import com.techyourchance.coroutines.demonstrations.noncancellable.NonCancellableDemoFragment
import com.techyourchance.coroutines.exercises.exercise8.Exercise8Fragment
import com.techyourchance.coroutines.demonstrations.scopecancellation.ScopeCancellationDemoFragment
import com.techyourchance.coroutines.demonstrations.scopechildrencancellation.ScopeChildrenCancellationDemoFragment
import com.techyourchance.coroutines.demonstrations.uithread.UiThreadDemoFragment
import com.techyourchance.coroutines.demonstrations.uncaughtexception.UncaughtExceptionDemoFragment
import com.techyourchance.coroutines.demonstrations.viewmodel.ViewModelDemoFragment
import com.techyourchance.coroutines.exercises.exercise1.Exercise1Fragment
import com.techyourchance.coroutines.exercises.exercise10.Exercise10Fragment
import com.techyourchance.coroutines.exercises.exercise2.Exercise2Fragment
import com.techyourchance.coroutines.exercises.exercise3.Exercise3Fragment
import com.techyourchance.coroutines.exercises.exercise4.Exercise4Fragment
import com.techyourchance.coroutines.exercises.exercise5.Exercise5Fragment
import com.techyourchance.coroutines.exercises.exercise6.Exercise6Fragment
import com.techyourchance.coroutines.exercises.exercise9.Exercise9Fragment
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

    fun toBasicCoroutinesDemo() {
        fragNavController.pushFragment(BasicCoroutinesDemoFragment.newInstance())
    }

    fun toExercise1() {
        fragNavController.pushFragment(Exercise1Fragment.newInstance())
    }

    fun toCoroutinesCancellationDemo() {
        fragNavController.pushFragment(CoroutinesCancellationDemoFragment.newInstance())
    }

    fun toExercise2() {
        fragNavController.pushFragment(Exercise2Fragment.newInstance())
    }

    fun toConcurrentCoroutines() {
        fragNavController.pushFragment(ConcurrentCoroutinesDemoFragment.newInstance())
    }

    fun toScopeChildrenCancellation() {
        fragNavController.pushFragment(ScopeChildrenCancellationDemoFragment.newInstance())
    }

    fun toExercise3() {
        fragNavController.pushFragment(Exercise3Fragment.newInstance())
    }

    fun toScopeCancellation() {
        fragNavController.pushFragment(ScopeCancellationDemoFragment.newInstance())
    }

    fun toViewModel() {
        fragNavController.pushFragment(ViewModelDemoFragment.newInstance())
    }

    fun toExercise4() {
        fragNavController.pushFragment(Exercise4Fragment.newInstance())
    }

    fun toDesignDemo() {
        fragNavController.pushFragment(DesignDemoFragment.newInstance())
    }

    fun toExercise5() {
        fragNavController.pushFragment(Exercise5Fragment.newInstance())
    }

    fun toCoroutinesCancellationCooperativeDemo() {
        fragNavController.pushFragment(CoroutinesCancellationCooperativeDemoFragment.newInstance())
    }

    fun toCoroutinesCancellationCooperative2Demo() {
        fragNavController.pushFragment(CoroutinesCancellationCooperative2DemoFragment.newInstance())
    }

    fun toExercise6() {
        fragNavController.pushFragment(Exercise6Fragment.newInstance())
    }

    fun toNonCancellable() {
        fragNavController.pushFragment(NonCancellableDemoFragment.newInstance())
    }

    fun toExercise8() {
        fragNavController.pushFragment(Exercise8Fragment.newInstance())
    }

    fun toExercise9() {
        fragNavController.pushFragment(Exercise9Fragment.newInstance())
    }

    fun toUncaughtException() {
        fragNavController.pushFragment(UncaughtExceptionDemoFragment.newInstance())
    }

    fun toExercise10() {
        fragNavController.pushFragment(Exercise10Fragment.newInstance())
    }

}