package com.endcodev.myinvoice.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InvoicesDao {
    //get all if post value is true
    @Query("SELECT * FROM invoices_table")
    suspend fun getAllInvoices(): List<InvoicesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInvoices(invoice: List<InvoicesEntity>)

    @Query("DELETE FROM invoices_table")
    suspend fun deleteAllInvoices()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: InvoicesEntity)

    @Query("DELETE FROM invoices_table WHERE iId = :id")
    fun deleteInvoice(id: String)

    @Query("SELECT * FROM invoices_table WHERE iId = :id")
    fun getInvoiceById(id: String): InvoicesEntity
}
