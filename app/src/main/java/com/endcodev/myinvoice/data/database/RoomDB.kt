package com.endcodev.myinvoice.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CustomersEntity::class],
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCustomerDao(): CustomersDao
}
