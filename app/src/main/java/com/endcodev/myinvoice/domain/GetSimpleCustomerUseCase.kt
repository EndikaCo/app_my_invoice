package com.endcodev.myinvoice.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.repository.CustomersRepository
import javax.inject.Inject

class GetSimpleCustomerUseCase @Inject constructor(
    private val repository: CustomersRepository
) : ViewModel() {

    companion object {
        const val TAG = "GetSimpleCustomerUseCase"
    }

    suspend operator fun invoke(customerIdentifier: String?): CustomerModel? {

        var customer : CustomerModel? = null

        if(customerIdentifier == null)
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
}