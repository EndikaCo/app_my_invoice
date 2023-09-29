package com.endcodev.myinvoice.domain

import android.util.Log
import com.endcodev.myinvoice.data.CustomerModel
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.repository.CustomersRepository
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(
    private val repository: CustomersRepository
) {

    companion object {
        const val TAG = "CustomersRepository"
    }

    suspend operator fun invoke(): List<CustomerModel>? {
        var customersList: List<CustomerModel>? = null

        try {
            customersList = repository.getAllCustomersFromDB()
        } catch (e: Exception) {
            Log.e(TAG, "No customers found, $e")
        }
        if (customersList.isNullOrEmpty()) {
            Log.v(TAG, "null customer list")
            repository.insertAllCustomers(exampleCustomers())
            customersList = repository.getAllCustomersFromDB()
        }
        return customersList
    }

    private fun exampleCustomers(): MutableList<CustomersEntity> {
        return arrayListOf(
            CustomersEntity(1, "Example 1", "B95768523", "+34623213213"),
            CustomersEntity(2, "Example 2", "1608876623V", "+86732132133"),
            CustomersEntity(3, "Example 3", "A323145125212", "+51624223213")
        )
    }

    fun savePlayer(customer: CustomersEntity?) {
        if (customer != null) {
            repository.insertCustomer(customer)
            Log.e(TAG, "customer ${customer.cFiscalName} inserted")
        }
    }

    fun deletePlayer(id: String) {
        repository.deleteCustomer(id)
        Log.e(TAG, "customer with $id deleted ")
    }
}