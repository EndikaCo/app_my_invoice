package com.endcodev.myinvoice.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.endcodev.myinvoice.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getAllItems(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(item: List<ProductEntity>)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ProductEntity)

    @Query("DELETE FROM product_table WHERE id = :code")
    fun deleteItem(code: String)

    @Query("SELECT * FROM product_table WHERE id = :itemId")
     fun getItemById(itemId: String): ProductEntity
}
