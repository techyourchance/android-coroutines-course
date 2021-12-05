package com.techyourchance.coroutines.demonstrations.noncancellable

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

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