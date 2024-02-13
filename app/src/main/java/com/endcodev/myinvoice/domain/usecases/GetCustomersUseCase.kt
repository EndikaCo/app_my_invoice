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

    companion object {

        const val TAG = "GetCustomersUseCase"

        fun exampleCustomers(): List<CustomersEntity> {
            return arrayListOf(

                CustomersEntity(
                    image = null,
                    fiscalName = "Toyota Motor Corporation",
                    id = "KDJ9576852",
                    telephone = "+81 623213276",
                    country = "Japan"
                ),
                CustomersEntity(
                    image = null,
                    fiscalName = "Alibaba Group Holding Limited",
                    id = "08876623VT",
                    telephone = "+86 732132133",
                    country = "China"

                ),
                CustomersEntity(
                    image = null,
                    fiscalName = "Vodafone Group plc",
                    id = "HJ3145125212",
                    telephone = "+44 624223213",
                    country = "United Kingdom"
                ),
                CustomersEntity(
                    image = null,
                    fiscalName = "Samsung Electronics Co., Ltd.",
                    id = "HSG23145112F",
                    telephone = "+82 624223213",
                    country = "South Korea"
                )
            )
        }
    }
}