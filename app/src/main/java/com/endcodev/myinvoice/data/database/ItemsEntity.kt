package com.endcodev.myinvoice.data.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.domain.models.ItemModel

@Entity(tableName = "items_table")
data class ItemsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "iCode")  val iCode: String,
    @ColumnInfo(name = "iImage") val iImage: String?,
    @ColumnInfo(name = "iName") val iName: String,
    @ColumnInfo(name = "iDescription") val iDescription: String,
    @ColumnInfo(name = "iType") val iType: String,
){
    val iImageUri: Uri?
        get() = iImage?.let { Uri.parse(it) }
}

fun ItemsEntity.toDomain() = ItemModel(
    iImage = iImageUri,
    iCode = iCode,
    iName = iName,
    iDescription = iDescription,
    iType = iType,
)