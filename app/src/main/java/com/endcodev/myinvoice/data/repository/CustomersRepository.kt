package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.data.database.daos.CustomersDao
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.data.database.entities.toDomain
import javax.inject.Inject

class CustomersRepository @Inject constructor(
    private val customersDao: CustomersDao,
) {
    suspend fun getAllCustomersFromDB(): List<Customer> {
        val customersList: List<CustomersEntity> = customersDao.getAllCustomers()
        return customersList.map { it.toDomain() }
    }

    fun getCustomerById(id: String): Customer {
        val customer: CustomersEntity = customersDao.getCustomerById(id)
        return customer.toDomain()
    }

    suspend fun insertAllCustomers(customersList: List<CustomersEntity>) {
        customersDao.insertAllCustomers(customersList)
    }

    suspend fun insertCustomer(customer: CustomersEntity) {
        customersDao.insertCustomer(customer)
    }

    fun deleteCustomer(id: String) {
        customersDao.deleteCustomer(id)
    }
}
