package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.data.repository.ProductsRepository
import com.endcodev.myinvoice.domain.utils.App
import javax.inject.Inject

class GetSimpleItemsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {

    operator fun invoke(itemId: String?): Product? {

        var item: Product? = null

        if (itemId == null)
            return null
        try {
            item = repository.getItemById(itemId)
        } catch (e: Exception) {
            Log.e(App.tag, "No items found, $e")
        }
        return item
    }

    suspend fun saveItem(item: ProductEntity?) {
        if (item != null) {
            repository.insertItem(item)
        }
    }

    suspend fun deleteProduct(id: String) {
        repository.deleteProduct(id)
        Log.e(App.tag, "customer with $id deleted")
    }
}
