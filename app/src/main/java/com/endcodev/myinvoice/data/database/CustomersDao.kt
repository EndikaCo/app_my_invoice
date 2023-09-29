package com.endcodev.myinvoice.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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
    fun insertPlayer(player: CustomersEntity)

    @Query("DELETE FROM customer_table WHERE cId = :cId")
    fun deletePlayer(cId: String)
}
