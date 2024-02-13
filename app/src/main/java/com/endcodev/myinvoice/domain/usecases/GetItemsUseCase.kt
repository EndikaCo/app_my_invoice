package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.data.repository.ProductsRepository
import com.endcodev.myinvoice.domain.models.product.Product
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    suspend operator fun invoke(): List<Product> {
        var itemsList: List<Product>? = null

        try {
            itemsList = productsRepository.getAllItemsFromDB()
        } catch (e: Exception) {
            Log.e(TAG, "No items found, $e")
        }
        if (itemsList.isNullOrEmpty()) {
            Log.v(TAG, "null items list")
            productsRepository.insertAllItems(exampleProducts())
            itemsList = productsRepository.getAllItemsFromDB()
        }
        return itemsList
    }

    companion object {
        const val TAG = "GetItemsUseCase"

        fun exampleProducts(): List<ProductEntity> {
            return arrayListOf(
                ProductEntity(
                    id = "BFP420E6327",
                    description = "40V 800mA 300MHz",
                    name = "Transistor 2N2222A",
                    image = null,
                    type = "Transistor NPN",
                    price = 0.5f,
                    cost = 0.3f,
                    stock = 100f
                ),
                ProductEntity(
                    id = "2449-A61C",
                    description = "Relay automotive SPDT 30A 12V",
                    name = "30A 12V Relay",
                    image = null,
                    type = "12VCC Relay",
                    price = 4.5f,
                    cost = 3.5f,
                    stock = 50f
                ),
                ProductEntity(
                    id = "PRC-231DDD",
                    description = "230VAC 8W 90CRI 2700K",
                    name = "Led GU10 2K7",
                    image = null,
                    type = "AC Led Lamp",
                    price = 10.5f,
                    cost = 8.5f,
                    stock = 100f
                ),
            )
        }
    }
}