package com.endcodev.myinvoice.data.database.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.domain.models.product.Product

@Entity(tableName = "items_table")
data class ItemsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "iCode")  val iCode: String,
    @ColumnInfo(name = "iImage") val iImage: String?,
    @ColumnInfo(name = "iName") val iName: String,
    @ColumnInfo(name = "iDescription") val iDescription: String,
    @ColumnInfo(name = "iType") val iType: String,
    @ColumnInfo(name = "iPrice") val iPrice: Float,
    @ColumnInfo(name = "iCost") val iCost: Float,
    @ColumnInfo(name = "iStock") val iStock: Float,
){
    val iImageUri: Uri?
        get() = iImage?.let { Uri.parse(it) }
}

fun ItemsEntity.toDomain() = Product(
    iImage = iImageUri,
    iCode = iCode,
    iName = iName,
    iDescription = iDescription,
    iType = iType,
    iPrice = iPrice,
    iCost = iCost,
    iStock = iStock,
)