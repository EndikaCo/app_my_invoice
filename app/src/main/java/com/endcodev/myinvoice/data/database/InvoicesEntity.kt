package com.endcodev.myinvoice.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.InvoicesModel

@Entity(tableName = "invoices_table")
data class InvoicesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iId") val iId: Int = 0,
    @ColumnInfo(name = "iCustomer") val iCustomer: String
    )

fun InvoicesEntity.toDomain() = InvoicesModel( iId = iId, iCustomer =  iCustomer)