package com.endcodev.myinvoice.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.domain.models.invoice.Invoice

@Entity(tableName = "invoices_table")
data class InvoicesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iId") val iId: Int = 0,
    @ColumnInfo(name = "iCustomer") val iCustomer: String,
    @ColumnInfo(name = "iDate") val iDate: String,
    @ColumnInfo(name = "iSaleList") val iSaleList: String,
)

fun InvoicesEntity.toDomain() =

    Converters().jsonToCustomerEntity(iCustomer)?.let {
        Invoice(
            iId = iId,
            iDate = iDate,
            iCustomer = it.toDomain(),
            iSaleList = Converters().jsonToSaleList(iSaleList),
        )
    }
