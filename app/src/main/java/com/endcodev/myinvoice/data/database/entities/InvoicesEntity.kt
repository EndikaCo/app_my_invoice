package com.endcodev.myinvoice.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.domain.models.InvoicesModel

@Entity(tableName = "invoices_table")
data class InvoicesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iId") val iId: Int = 0,
    @ColumnInfo(name = "iCustomer") val iCustomer: String,
)

fun InvoicesEntity.toDomain() =
    Converters().toCustomersEntity(iCustomer)?.let {
        InvoicesModel(
        iId = iId,
        iCustomer = it.toDomain()
        )
    }