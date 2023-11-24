package com.endcodev.myinvoice.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.endcodev.myinvoice.data.database.entities.CustomersEntity

@Dao
interface CustomersDao {

    //get all if post value is true
    @Query("SELECT * FROM customer_table")
    suspend fun getAllCustomers(): List<CustomersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCustomers(customer: List<CustomersEntity>)

    @Query("DELETE FROM customer_table")
    suspend fun deleteAllCustomers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(player: CustomersEntity)

    @Query("DELETE FROM customer_table WHERE cIdentifier = :cId")
    fun deleteCustomer(cId: String)

    @Query("SELECT * FROM customer_table WHERE cIdentifier = :cId")
    fun getCustomerById(cId: String): CustomersEntity
}