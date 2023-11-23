package com.endcodev.myinvoice.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.endcodev.myinvoice.data.Converters

@Database(
    entities = [
        CustomersEntity::class,
        InvoicesEntity::class,
        ItemsEntity::class
    ],
    version = 10
)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCustomerDao(): CustomersDao
    abstract fun getItemsDao(): ItemsDao
    abstract fun getInvoicesDao(): InvoicesDao
}
