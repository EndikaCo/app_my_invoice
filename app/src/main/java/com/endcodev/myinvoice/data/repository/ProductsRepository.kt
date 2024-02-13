package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.database.daos.ProductDao
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.data.database.entities.toDomain
import com.endcodev.myinvoice.domain.models.product.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productDao : ProductDao
) {
    suspend fun getAllItemsFromDB() : List<Product> {
        val response : List<ProductEntity> = productDao.getAllItems()
        return response.map { it.toDomain() }
    }

    fun getItemById(itemId : String) : Product {
        val response : ProductEntity = productDao.getItemById(itemId)
        return response.toDomain()
    }

    suspend fun insertAllItems(itemsList: List<ProductEntity>) {
        productDao.insertAllItems(itemsList)
    }

    suspend fun insertItem(item : ProductEntity) {
        productDao.insertItem(item)
    }

    fun deleteItem() {
    }
}