package com.endcodev.myinvoice.data.database.entities

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.utils.App

@Entity(tableName = "sale_table")
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val product: String,
    val price: Float = 0f,
    val quantity: Float = 1f,
    val discount: Int = 0,
    val tax: Int = 0,
) {
    val domainProduct: Product
        get() {
            return try {
                return Converters().jsonToProductEntity(product)?.toDomain()!! //todo
            } catch (e: Exception) {
                Log.e(App.tag, "error jsonToSaleList${e.message}")
                Product(
                        null,
                        "",
                        "",
                        "",
                        "",
                        0f,
                        0f,
                        0f,
                    )
            }
        }
}

fun SaleEntity.toDomain()=
    SaleItem(
        product = domainProduct,
        id = id,
        price = price,
        quantity = quantity,
        discount = discount,
        tax = tax,
    )
