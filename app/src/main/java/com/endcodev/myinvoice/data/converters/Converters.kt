package com.endcodev.myinvoice.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.data.database.entities.ItemsEntity
import com.endcodev.myinvoice.data.database.entities.SaleEntity
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.product.Product
import com.google.gson.Gson

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun customerEntityToJson(customer: CustomersEntity?): String? {
        return Gson().toJson(customer)
    }

    @TypeConverter
    fun jsonToCustomerEntity(json: String?): CustomersEntity? {
        return Gson().fromJson(json, CustomersEntity::class.java)
    }

    @TypeConverter
    fun productEntityToJson(items: Product?): String? {
        return Gson().toJson(items)
    }

    @TypeConverter
    fun jsonToProductEntity(json: String?): ItemsEntity? {
        return Gson().fromJson(json, ItemsEntity::class.java)
    }

    @TypeConverter
    fun saleListToJson(saleList: List<SaleEntity>): String {
        return Gson().toJson(saleList)
    }

    @TypeConverter
    fun jsonToSaleList(json: String?): List<SaleEntity> {
        return Gson().fromJson(json, Array<SaleEntity>::class.java).toList()
    }
}