package com.endcodev.myinvoice.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
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
}