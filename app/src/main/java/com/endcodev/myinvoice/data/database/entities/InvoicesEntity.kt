package com.endcodev.myinvoice.data.database.entities

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.utils.App

@Entity(tableName = "invoices_table")
data class InvoicesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iId") val iId: Int = 0,
    @ColumnInfo(name = "iCustomer") val iCustomer: String,
    @ColumnInfo(name = "iDate") val iDate: String,
    @ColumnInfo(name = "iSaleList") val iSaleList: String,
) {
    val domainCustomer: Customer
        get() = Converters().jsonToCustomerEntity(iCustomer)?.toDomain() ?: Customer(null, "", "")

    val domainSales: List<SaleItem>
        get() {
            return try {
                Converters().jsonToSaleList(iSaleList).map { it.toDomain()!! } //todo
            } catch (e: Exception) {
                Log.e(App.tag, "error jsonToSaleList${e.message}")
                emptyList()
            }
        }
}

fun InvoicesEntity.toDomain() =
    Invoice(
        iId = iId,
        iDate = iDate,
        iCustomer = domainCustomer,
        iSaleList = domainSales
    )


