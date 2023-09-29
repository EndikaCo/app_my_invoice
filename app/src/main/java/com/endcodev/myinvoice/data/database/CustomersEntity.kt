package com.endcodev.myinvoice.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endcodev.myinvoice.data.CustomerModel

@Entity(tableName = "customer_table")
data class CustomersEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cId") val cId: Int = 0,
    @ColumnInfo(name = "cIdentifier") val cIdentifier: String,
    @ColumnInfo(name = "cFiscalName") val cFiscalName : String,
    @ColumnInfo(name = "cTelephone") val cTelephone : String
    )

fun CustomersEntity.toDomain() = CustomerModel(cId = cId, null, cIdentifier, cFiscalName, cTelephone)