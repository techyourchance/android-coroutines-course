package com.techyourchance.coroutines.demonstrations.noncancellable

import com.techyourchance.coroutines.common.ThreadInfoLogger
import com.techyourchance.coroutines.common.ThreadInfoLogger.logThreadInfo
import com.techyourchance.coroutines.exercises.exercise6.PostBenchmarkResultsEndpoint
import kotlinx.coroutines.*

class MakeCustomerPremiumUseCase(
        private val premiumCustomersEndpoint: PremiumCustomersEndpoint,
        private val customersDao: CustomersDao
) {

    /**
     * Give the customer premium status
     * @return updated information about the customer
     */
    suspend fun makeCustomerPremium(customerId: String): Customer {
        return withContext(Dispatchers.Default) {
            withContext(NonCancellable) {
                val updatedCustomer = premiumCustomersEndpoint.makeCustomerPremium(customerId)
                customersDao.updateCustomer(updatedCustomer)
                updatedCustomer
            }
        }
    }

}