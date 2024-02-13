package com.endcodev.myinvoice.data.database.entities

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.utils.App

@Entity(tableName = "invoice_table")
data class InvoicesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customer: String,
    val date: String,
    val saleList: String,
) {
    val domainCustomer: Customer
        get() = Converters().jsonToCustomerEntity(customer)?.toDomain() ?: Customer(null, "", "")

    val domainSales: List<SaleItem>
        get() {
            return try {
                Converters().jsonToSaleList(saleList).map { it.toDomain() } //todo
            } catch (e: Exception) {
                Log.e(App.tag, "error jsonToSaleList${e.message}")
                emptyList()
            }
        }
}

fun InvoicesEntity.toDomain() =
    Invoice(
        id = id,
        date = date,
        customer = domainCustomer,
        saleList = domainSales.toMutableList()
    )


