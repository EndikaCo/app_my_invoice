package com.endcodev.myinvoice.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.data.database.daos.CustomersDao
import com.endcodev.myinvoice.data.database.daos.InvoicesDao
import com.endcodev.myinvoice.data.database.daos.ProductDao
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.data.database.entities.InvoicesEntity
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.data.database.entities.SaleEntity

@Database(
    entities = [
        CustomersEntity::class,
        InvoicesEntity::class,
        SaleEntity::class,
        ProductEntity::class
    ],
    version = 3
)

@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCustomerDao(): CustomersDao
    abstract fun getItemsDao(): ProductDao
    abstract fun getInvoicesDao(): InvoicesDao
}
