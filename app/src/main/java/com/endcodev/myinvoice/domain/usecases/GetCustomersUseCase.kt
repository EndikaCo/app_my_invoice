package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.data.repository.CustomersRepository
import javax.inject.Inject

class GetCustomersUseCase @Inject constructor(
    private val repository: CustomersRepository
) {

    suspend operator fun invoke(): List<Customer> {
        var customersList: List<Customer>? = null

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
                    cFiscalName = "Toyota Motor Corporation",
                    cIdentifier = "KDJ9576852",
                    cTelephone = "+81 623213276",
                    cCountry = "Japan"
                ),
                CustomersEntity(
                    cImage = null,
                    cFiscalName = "Alibaba Group Holding Limited",
                    cIdentifier = "08876623VT",
                    cTelephone = "+86 732132133",
                    cCountry = "China"

                ),
                CustomersEntity(
                    cImage = null,
                    cFiscalName = "Vodafone Group plc",
                    cIdentifier = "HJ3145125212",
                    cTelephone = "+44 624223213",
                    cCountry = "United Kingdom"
                ),
                CustomersEntity(
                    cImage = null,
                    cFiscalName = "Samsung Electronics Co., Ltd.",
                    cIdentifier = "HSG23145112F",
                    cTelephone = "+82 624223213",
                    cCountry = "South Korea"
                )
            )
        }
    }
}