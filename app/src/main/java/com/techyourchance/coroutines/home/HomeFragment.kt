package com.techyourchance.coroutines.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.BaseFragment

class HomeFragment : BaseFragment(), HomeArrayAdapter.Listener {

    override val screenTitle get() = "Coroutines Course"

    private lateinit var listScreensReachableFromHome: ListView
    private lateinit var adapterScreensReachableFromHome: HomeArrayAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        adapterScreensReachableFromHome = HomeArrayAdapter(requireContext(), this)

        listScreensReachableFromHome = view.findViewById(R.id.list_screens)
        listScreensReachableFromHome.adapter = adapterScreensReachableFromHome

        adapterScreensReachableFromHome.addAll(*ScreenReachableFromHome.values())
        adapterScreensReachableFromHome.notifyDataSetChanged()

        return view
    }

    override fun onScreenClicked(screenReachableFromHome: ScreenReachableFromHome) {
        when (screenReachableFromHome) {
            ScreenReachableFromHome.UI_THREAD_DEMO -> screensNavigator.toUiThreadDemo()
            ScreenReachableFromHome.BACKGROUND_THREAD_DEMO -> screensNavigator.toBackgroundThreadDemo()
            ScreenReachableFromHome.BASIC_COROUTINES_DEMO -> screensNavigator.toBasicCoroutinesDemo()
            ScreenReachableFromHome.EXERCISE_1 -> screensNavigator.toExercise1()
            ScreenReachableFromHome.COROUTINES_CANCELLATION_DEMO -> screensNavigator.toCoroutinesCancellationDemo()
            ScreenReachableFromHome.EXERCISE_2 -> screensNavigator.toExercise2()
            ScreenReachableFromHome.CONCURRENT_COROUTINES_DEMO -> screensNavigator.toConcurrentCoroutines()
            ScreenReachableFromHome.SCOPE_CHILDREN_CANCELLATION_DEMO -> screensNavigator.toScopeChildrenCancellation()
            ScreenReachableFromHome.EXERCISE_3 -> screensNavigator.toExercise3()
            ScreenReachableFromHome.SCOPE_CANCELLATION_DEMO -> screensNavigator.toScopeCancellation()
            ScreenReachableFromHome.VIEWMODEL_DEMO -> screensNavigator.toViewModel()
            ScreenReachableFromHome.EXERCISE_4 -> screensNavigator.toExercise4()
            ScreenReachableFromHome.DESIGN_DEMO -> screensNavigator.toDesignDemo()
            ScreenReachableFromHome.EXERCISE_5 -> screensNavigator.toExercise5()
            ScreenReachableFromHome.COROUTINES_CANCELLATION_COOPERATIVE_DEMO -> screensNavigator.toCoroutinesCancellationCooperativeDemo()
            ScreenReachableFromHome.COROUTINES_CANCELLATION_COOPERATIVE_2_DEMO -> screensNavigator.toCoroutinesCancellationCooperative2Demo()
            ScreenReachableFromHome.EXERCISE_6 -> screensNavigator.toExercise6()
            ScreenReachableFromHome.NON_CANCELLABLE_DEMO -> screensNavigator.toNonCancellable()
            ScreenReachableFromHome.EXERCISE_8 -> screensNavigator.toExercise8()
            ScreenReachableFromHome.EXERCISE_9 -> screensNavigator.toExercise9()
            ScreenReachableFromHome.UNCAUGHT_EXCEPTION_DEMO -> screensNavigator.toUncaughtException()
            ScreenReachableFromHome.EXERCISE_10 -> screensNavigator.toExercise10()
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }
}