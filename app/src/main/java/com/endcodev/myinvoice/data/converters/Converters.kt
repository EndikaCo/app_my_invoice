package com.endcodev.myinvoice.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.data.database.entities.SaleEntity
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
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
    fun productEntityToJson(items: ProductEntity?): String? {
        return Gson().toJson(items)
    }

    @TypeConverter
    fun jsonToProductEntity(json: String?): ProductEntity? {
        return Gson().fromJson(json, ProductEntity::class.java)
    }

    @TypeConverter
    fun salesEntityToJson(saleList: List<SaleItem>): String {

        val saleListWithJsonProduct = saleList.map {
            it.toEntity()
        }
        return Gson().toJson(saleListWithJsonProduct)
    }

    @TypeConverter
    fun jsonToSaleList(json: String?): List<SaleEntity> {
        return Gson().fromJson(json, Array<SaleEntity>::class.java).toList()
    }
}