package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.database.ItemsDao
import com.endcodev.myinvoice.data.database.ItemsEntity
import com.endcodev.myinvoice.data.database.toDomain
import com.endcodev.myinvoice.data.model.ItemModel
import javax.inject.Inject

class ItemsRepository @Inject constructor(
    private val itemsDao : ItemsDao
) {
    suspend fun getAllItemsFromDB() : List<ItemModel> {
        val response : List<ItemsEntity> = itemsDao.getAllItems()
        return response.map { it.toDomain() }
    }

    suspend fun insertAllItems(itemsList: List<ItemsEntity>) {
        itemsDao.insertAllItems(itemsList)
    }

    suspend fun insertItem(item : ItemsEntity) {
        itemsDao.insertItem(item)
    }

    fun deleteItem() {
    }
}