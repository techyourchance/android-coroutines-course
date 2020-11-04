package com.techyourchance.coroutines.common.dependencyinjection

import androidx.fragment.app.FragmentActivity
import com.ncapdevi.fragnav.FragNavController
import com.techyourchance.coroutines.R
import com.techyourchance.coroutines.common.ScreensNavigator
import com.techyourchance.coroutines.common.ToolbarDelegate
import com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative.CancellableBenchmarkUseCase
import com.techyourchance.coroutines.demonstrations.coroutinescancellationcooperative2.BlockingBenchmarkUseCase
import com.techyourchance.coroutines.demonstrations.design.BenchmarkUseCase
import com.techyourchance.coroutines.demonstrations.noncancellable.CustomersDao
import com.techyourchance.coroutines.demonstrations.noncancellable.MakeCustomerPremiumUseCase
import com.techyourchance.coroutines.demonstrations.noncancellable.PremiumCustomersEndpoint
import com.techyourchance.coroutines.demonstrations.uncaughtexception.LoginEndpointUncaughtException
import com.techyourchance.coroutines.demonstrations.uncaughtexception.LoginUseCaseUncaughtException
import com.techyourchance.coroutines.demonstrations.uncaughtexception.UserStateManager
import com.techyourchance.coroutines.exercises.exercise8.FetchAndCacheUsersUseCase
import com.techyourchance.coroutines.exercises.exercise8.GetUserEndpoint
import com.techyourchance.coroutines.exercises.exercise8.UsersDao
import com.techyourchance.coroutines.exercises.exercise1.GetReputationEndpoint
import com.techyourchance.coroutines.exercises.exercise4.FactorialUseCase
import com.techyourchance.coroutines.exercises.exercise6.Exercise6BenchmarkUseCase
import com.techyourchance.coroutines.exercises.exercise6.PostBenchmarkResultsEndpoint
import com.techyourchance.coroutines.exercises.exercise9.FetchAndCacheUsersUseCaseExercise9
import com.techyourchance.coroutines.solutions.exercise5.GetReputationUseCase
import com.techyourchance.coroutines.solutions.exercise6.Exercise6SolutionBenchmarkUseCase
import com.techyourchance.coroutines.solutions.exercise8.Exercise8SolutionFetchAndCacheUsersUseCase
import com.techyourchance.coroutines.solutions.exercise9.FetchAndCacheUsersUseCaseSolutionExercise9

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

    private val premiumCustomersEndpoint get() = PremiumCustomersEndpoint()

    private val customersDao get() = CustomersDao()

    private val getUserEndpoint get() = GetUserEndpoint()

    private val usersDao get() = UsersDao()

    private val loginEndpointUncaughtException get() = LoginEndpointUncaughtException()

    private val userStateManager get() = UserStateManager()

    val getReputationEndpoint get() = GetReputationEndpoint()

    val factorialUseCase get() = FactorialUseCase()

    val benchmarkUseCase get() = BenchmarkUseCase()

    val cancellableBenchmarkUseCase get() = CancellableBenchmarkUseCase()

    val blockingBenchmarkUseCase get() = BlockingBenchmarkUseCase()

    val exercise6BenchmarkUseCase get() = Exercise6BenchmarkUseCase(postBenchmarkResultsEndpoint)

    val exercise6SolutionBenchmarkUseCase get() = Exercise6SolutionBenchmarkUseCase(postBenchmarkResultsEndpoint)

    val getReputationUseCase get() = GetReputationUseCase(getReputationEndpoint)

    val makeCustomerPremiumUseCase get() = MakeCustomerPremiumUseCase(premiumCustomersEndpoint, customersDao)

    val fetchAndCacheUserUseCase get() = FetchAndCacheUsersUseCase(getUserEndpoint, usersDao)

    val exercise8SolutionFetchAndCacheUserUseCase get() = Exercise8SolutionFetchAndCacheUsersUseCase(getUserEndpoint, usersDao)

    val fetchAndCacheUserUseCaseExercise9 get() = FetchAndCacheUsersUseCaseExercise9(getUserEndpoint, usersDao)

    val fetchAndCacheUserUseCaseSolutionExercise9 get() = FetchAndCacheUsersUseCaseSolutionExercise9(getUserEndpoint, usersDao)

    val loginUseCaseUncaughtException get() = LoginUseCaseUncaughtException(loginEndpointUncaughtException, userStateManager)

}