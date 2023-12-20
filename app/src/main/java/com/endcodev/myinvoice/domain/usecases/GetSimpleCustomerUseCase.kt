package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.data.repository.CustomersRepository
import javax.inject.Inject

class GetSimpleCustomerUseCase @Inject constructor(
    private val repository: CustomersRepository
) : ViewModel() {

    companion object {
        const val TAG = "GetSimpleCustomerUseCase"
    }

    operator fun invoke(customerIdentifier: String?): Customer? {

        var customer: Customer? = null

        if (customerIdentifier == null)
            return null

        try {
            customer = repository.getCustomerById(customerIdentifier)
        } catch (e: Exception) {
            Log.e(TAG, "customer not found, $e")
        }
        return customer
    }

    suspend fun saveCustomer(customer: CustomersEntity?) {
        if (customer != null) {
            repository.insertCustomer(customer)
            Log.v(GetCustomersUseCase.TAG, "customer ${customer.cFiscalName} inserted")
        }
    }

    fun deleteCustomer(id: String) {
        repository.deleteCustomer(id)
        Log.e(GetCustomersUseCase.TAG, "customer with $id deleted")
    }
}