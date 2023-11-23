package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.database.ItemsDao
import com.endcodev.myinvoice.data.database.ItemsEntity
import com.endcodev.myinvoice.data.database.toDomain
import com.endcodev.myinvoice.domain.models.ItemModel
import javax.inject.Inject

class ItemsRepository @Inject constructor(
    private val itemsDao : ItemsDao
) {
    suspend fun getAllItemsFromDB() : List<ItemModel> {
        val response : List<ItemsEntity> = itemsDao.getAllItems()
        return response.map { it.toDomain() }
    }

    fun getItemById(itemId : String) : ItemModel {
        val response : ItemsEntity = itemsDao.getItemById(itemId)
        return response.toDomain()
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