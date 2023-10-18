package com.endcodev.myinvoice.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.ItemsModel

@Entity(tableName = "items_table")
data class ItemsEntity(
    @PrimaryKey(autoGenerate = false)
    val iImage: Int?,
    val iCode : String,
    val iName : String,
    val iDescription : String,
    )

fun ItemsEntity.toDomain() = ItemsModel(null, iCode = iCode,
    iName =  iName, iDescription = iDescription)