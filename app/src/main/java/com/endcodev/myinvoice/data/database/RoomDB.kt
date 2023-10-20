package com.endcodev.myinvoice.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CustomersEntity::class,
        InvoicesEntity::class,
        ItemsEntity::class
    ],
    version = 3
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCustomerDao(): CustomersDao
    abstract fun getItemsDao(): ItemsDao
    abstract fun getInvoicesDao(): InvoicesDao
}
