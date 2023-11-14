package com.endcodev.myinvoice.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {

    //get all if post value is true
    @Query("SELECT * FROM items_table")
    suspend fun getAllItems(): List<ItemsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(item: List<ItemsEntity>)

    @Query("DELETE FROM items_table")
    suspend fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemsEntity)

    @Query("DELETE FROM items_table WHERE iCode = :code")
    fun deleteItem(code: String)

    @Query("SELECT * FROM items_table WHERE iCode = :itemId")
     fun getItemById(itemId: String): ItemsEntity
}
