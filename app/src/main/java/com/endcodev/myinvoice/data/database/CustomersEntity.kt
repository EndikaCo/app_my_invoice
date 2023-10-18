package com.endcodev.myinvoice.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.model.CustomerModel

@Entity(tableName = "customer_table")
data class CustomersEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cIdentifier") val cIdentifier: String,
    @ColumnInfo(name = "cFiscalName") val cFiscalName: String,
    @ColumnInfo(name = "cTelephone") val cTelephone: String
)

fun CustomersEntity.toDomain() = CustomerModel(
    cImage = null,
    cIdentifier = cIdentifier,
    cFiscalName = cFiscalName,
    cTelephone = cTelephone
)