package com.endcodev.myinvoice.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val DB = "my_invoice_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomDB::class.java, DB).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCustomersDao(db: RoomDB) = db.getCustomerDao()

    @Singleton
    @Provides
    fun provideItemsDao(db: RoomDB) = db.getItemsDao()

    @Singleton
    @Provides
    fun provideInvoicesDao(db: RoomDB) = db.getInvoicesDao()
}