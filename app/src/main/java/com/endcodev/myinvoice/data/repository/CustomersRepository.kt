package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.CustomerModel
import com.endcodev.myinvoice.data.database.CustomersDao
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.database.toDomain
import javax.inject.Inject

class CustomersRepository @Inject constructor(
    private val customersDao: CustomersDao,

    ) {
    suspend fun getAllCustomersFromDB(): List<CustomerModel>? {
        val response: List<CustomersEntity> = customersDao.getAllCustomers()
        return response.map { it.toDomain() }
    }

    suspend fun insertAllCustomers(customersList: MutableList<CustomersEntity>) {
        customersDao.insertAllCustomers(customersList)
    }

    fun insertCustomer(customer: CustomersEntity) {
    }

    fun deleteCustomer(name: String) {
    }

}
