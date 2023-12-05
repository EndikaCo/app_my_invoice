package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.database.daos.ItemsDao
import com.endcodev.myinvoice.data.database.entities.ItemsEntity
import com.endcodev.myinvoice.data.database.entities.toDomain
import com.endcodev.myinvoice.domain.models.product.Product
import javax.inject.Inject

class ItemsRepository @Inject constructor(
    private val itemsDao : ItemsDao
) {
    suspend fun getAllItemsFromDB() : List<Product> {
        val response : List<ItemsEntity> = itemsDao.getAllItems()
        return response.map { it.toDomain() }
    }

    fun getItemById(itemId : String) : Product {
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