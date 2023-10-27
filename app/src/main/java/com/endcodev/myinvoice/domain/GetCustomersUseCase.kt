package com.endcodev.myinvoice.domain

import android.util.Log
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.repository.CustomersRepository
import javax.inject.Inject

class GetCustomersUseCase @Inject constructor(
    private val repository: CustomersRepository
) {

    suspend operator fun invoke(): List<CustomerModel> {
        var customersList: List<CustomerModel>? = null

        try {
            customersList = repository.getAllCustomersFromDB()
        } catch (e: Exception) {
            Log.e(TAG, "No customers found, $e")
        }
        if (customersList.isNullOrEmpty()) {
            repository.insertAllCustomers(exampleCustomers())
            customersList = repository.getAllCustomersFromDB()
        }
        return customersList
    }

    suspend fun saveCustomer(customer: CustomersEntity?) {
        if (customer != null) {
            repository.insertCustomer(customer)
            Log.v(TAG, "customer ${customer.cFiscalName} inserted")
        }
    }

    fun deleteCustomer(id: String) {
        repository.deleteCustomer(id)
        Log.e(TAG, "customer with $id deleted ")
    }

    companion object {

        const val TAG = "GetCustomersUseCase"

        fun exampleCustomers(): List<CustomersEntity> {
            return arrayListOf(

                CustomersEntity(
                    cImage = null,
                    cFiscalName = "Example 1",
                    cIdentifier = "B95768523",
                    cTelephone = "+34623213213"
                ),
                CustomersEntity(
                    cImage = null,
                    cFiscalName = "Example 2",
                    cIdentifier = "1608876623V",
                    cTelephone = "+86732132133"
                ),
                CustomersEntity(
                    cImage = null,
                    cFiscalName = "Example 3",
                    cIdentifier = "A323145125212",
                    cTelephone = "+51624223213"
                )
            )
        }
    }
}