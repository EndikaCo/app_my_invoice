package com.endcodev.myinvoice.data.database.entities

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.utils.App

@Entity(tableName = "invoices_table")
data class SaleEntity(
    @ColumnInfo(name = "sId") val sId: Int = 0,
    @ColumnInfo(name = "sProduct") val sProduct: String,
    @ColumnInfo(name = "sPrice") val sPrice: Float = 0f,
    @ColumnInfo(name = "sQuantity") val sQuantity: Float = 1f,
    @ColumnInfo(name = "sDiscount") val sDiscount: Int = 0,
    @ColumnInfo(name = "sTax") val sTax: Int = 0,
) {
    val domainProduct: Product
        get() {
            return try {
                return Converters().jsonToProductEntity(sProduct)?.toDomain()!! //todo
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
        sProduct = domainProduct,
        sId = sId,
        sPrice = sPrice,
        sQuantity = sQuantity,
        sDiscount = sDiscount,
        sTax = sTax,
    )
