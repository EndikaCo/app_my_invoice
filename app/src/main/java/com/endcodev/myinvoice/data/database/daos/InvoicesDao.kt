package com.endcodev.myinvoice.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.endcodev.myinvoice.data.database.entities.InvoicesEntity

@Dao
interface InvoicesDao {

    @Query("SELECT * FROM invoices_table")
    suspend fun getAllInvoices(): List<InvoicesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInvoices(invoice: List<InvoicesEntity>)

    @Query("DELETE FROM invoices_table")
    suspend fun deleteAllInvoices()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: InvoicesEntity)

    @Update
    suspend fun updateInvoice(invoice: InvoicesEntity)

    @Query("DELETE FROM invoices_table WHERE iId = :id")
    fun deleteInvoice(id: String)

    @Query("SELECT * FROM invoices_table WHERE iId = :id")
    fun getInvoiceById(id: String): InvoicesEntity
}
