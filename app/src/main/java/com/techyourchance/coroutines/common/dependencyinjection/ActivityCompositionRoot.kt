package com.techyourchance.coroutines.common.dependencyinjection

import androidx.fragment.app.FragmentActivity
import com.ncapdevi.fragnav.FragNavController
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.ScreensNavigator
import com.techyourchance.coroutines.common.ToolbarDelegate
import com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative.CancellableBenchmarkUseCase
import com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative2.BlockingBenchmarkUseCase
import com.techyourchance.coroutines.demonstrations.design.BenchmarkUseCase
import com.techyourchance.coroutines.exercises.exercise1.GetReputationEndpoint
import com.techyourchance.coroutines.exercises.exercise4.FactorialUseCase
import com.techyourchance.coroutines.exercises.exercise6.Exercise6BenchmarkUseCase
import com.techyourchance.coroutines.exercises.exercise6.PostBenchmarkResultsEndpoint
import com.techyourchance.coroutines.solutions.exercise5.GetReputationUseCase
import com.techyourchance.coroutines.solutions.exercise6.Exercise6SolutionBenchmarkUseCase

class ActivityCompositionRoot(
        private val activity: FragmentActivity,
        private val appCompositionRoot: ApplicationCompositionRoot
) {

    val toolbarManipulator get() = activity as ToolbarDelegate

    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(fragNavController)
    }

    private val fragNavController get() = FragNavController(fragmentManager, R.id.frame_content)

    private val fragmentManager get() = activity.supportFragmentManager

    private val postBenchmarkResultsEndpoint get() = PostBenchmarkResultsEndpoint()

    val getReputationEndpoint get() = GetReputationEndpoint()

    val factorialUseCase get() = FactorialUseCase()

    val benchmarkUseCase get() = BenchmarkUseCase()

    val cancellableBenchmarkUseCase get() = CancellableBenchmarkUseCase()

    val blockingBenchmarkUseCase get() = BlockingBenchmarkUseCase()

    val exercise6BenchmarkUseCase get() = Exercise6BenchmarkUseCase(postBenchmarkResultsEndpoint)

    val exercise6SolutionBenchmarkUseCase get() = Exercise6SolutionBenchmarkUseCase(postBenchmarkResultsEndpoint)

    val getReputationUseCase get() = GetReputationUseCase(getReputationEndpoint)
}