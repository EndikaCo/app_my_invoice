package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.domain.models.CustomerModel
import com.endcodev.myinvoice.data.database.CustomersDao
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.database.toDomain
import javax.inject.Inject

class CustomersRepository @Inject constructor(
    private val customersDao: CustomersDao,
    ) {
    suspend fun getAllCustomersFromDB(): List<CustomerModel> {
        val customersList: List<CustomersEntity> = customersDao.getAllCustomers()
        return customersList.map { it.toDomain() }
    }

    fun getCustomerById(id : String) : CustomerModel {
        val customer : CustomersEntity = customersDao.getCustomerById(id)
        return customer.toDomain()
    }

    suspend fun insertAllCustomers(customersList: List<CustomersEntity>) {
        customersDao.insertAllCustomers(customersList)
    }

    suspend fun insertCustomer(customer: CustomersEntity) {
        customersDao.insertCustomer(customer)
    }

    fun deleteCustomer(name: String) {
    }
}
