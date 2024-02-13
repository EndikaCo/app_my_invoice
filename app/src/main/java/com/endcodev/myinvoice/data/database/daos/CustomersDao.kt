package com.endcodev.myinvoice.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.data.database.entities.InvoicesEntity

@Dao
interface CustomersDao {

    @Query("SELECT * FROM customer_table")
    suspend fun getAllCustomers(): List<CustomersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCustomers(customer: List<CustomersEntity>)

    @Query("DELETE FROM customer_table")
    suspend fun deleteAllCustomers()

    @Update
    suspend fun updateCustomer(customer: CustomersEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(player: CustomersEntity)

    @Query("DELETE FROM customer_table WHERE id = :cId")
    fun deleteCustomer(cId: String)

    @Query("SELECT * FROM customer_table WHERE id = :cId")
    fun getCustomerById(cId: String): CustomersEntity
}
