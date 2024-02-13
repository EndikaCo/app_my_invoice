package com.endcodev.myinvoice.data.database.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.domain.models.product.Product

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val image: String?,
    val name: String,
    val description: String,
    val type: String,
    val price: Float,
    val cost: Float,
    val stock: Float,
){
    val iImageUri: Uri?
        get() = image?.let { Uri.parse(it) }
}

fun ProductEntity.toDomain() = Product(
    image = iImageUri,
    id = id,
    name = name,
    description = description,
    type = type,
    price = price,
    cost = cost,
    stock = stock,
)