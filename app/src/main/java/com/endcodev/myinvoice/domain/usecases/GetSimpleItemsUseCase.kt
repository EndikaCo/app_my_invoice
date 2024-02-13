package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.data.repository.ProductsRepository
import javax.inject.Inject

class GetSimpleItemsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    companion object {
        const val TAG = "GetSimpleItemsUseCase"
    }

    operator fun invoke(itemId: String?): Product? {

        var item: Product? = null

        if (itemId == null)
            return null
        try {
            item = productsRepository.getItemById(itemId)
        } catch (e: Exception) {
            Log.e(TAG, "No items found, $e")
        }
        return item
    }

    suspend fun saveItem(item: ProductEntity?) {
        if (item != null) {
            productsRepository.insertItem(item)
            Log.e(GetCustomersUseCase.TAG, "customer ${item.id} inserted")
        }
    }
}
