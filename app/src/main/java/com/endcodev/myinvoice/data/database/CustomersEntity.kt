package com.endcodev.myinvoice.data.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.domain.models.CustomerModel

@Entity(tableName = "customer_table")
data class CustomersEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cIdentifier") val cIdentifier: String,
    @ColumnInfo(name = "cImage") val cImage: String?,
    @ColumnInfo(name = "cFiscalName") val cFiscalName: String,
    @ColumnInfo(name = "cTelephone") val cTelephone: String,
    @ColumnInfo(name = "cCountry") val cCountry: String
){
    val cImageUri: Uri?
        get() = cImage?.let { Uri.parse(it) }
}

fun CustomersEntity.toDomain() = CustomerModel(
    cImage = cImageUri,
    cIdentifier = cIdentifier,
    cFiscalName = cFiscalName,
    cTelephone = cTelephone,
    cCountry = cCountry
)