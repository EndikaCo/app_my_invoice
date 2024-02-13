package com.endcodev.myinvoice.data.database.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.domain.models.customer.Customer

@Entity(tableName = "customer_table")
data class CustomersEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val image: String?,
    val fiscalName: String,
    val telephone: String,
    val country: String
){
    val imageUri: Uri?
        get() = image?.let { Uri.parse(it) }
}

fun CustomersEntity.toDomain() = Customer(
    image = imageUri,
    id = id,
    fiscalName = fiscalName,
    telephone = telephone,
    country = country
)