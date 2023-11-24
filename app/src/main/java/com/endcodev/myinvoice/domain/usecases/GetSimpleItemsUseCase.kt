package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.entities.ItemsEntity
import com.endcodev.myinvoice.domain.models.ItemModel
import com.endcodev.myinvoice.data.repository.ItemsRepository
import javax.inject.Inject

    class GetSimpleItemsUseCase @Inject constructor(
        private val itemsRepository: ItemsRepository
    ) {

        companion object { const val TAG = "GetSimpleItemsUseCase"}

        operator fun invoke(itemId : String ?): ItemModel? {

            var item: ItemModel? = null

            if (itemId == null)
                return null
            try {
                item = itemsRepository.getItemById(itemId)
            } catch (e: Exception) {
                Log.e(TAG, "No items found, $e")
            }
            return item
        }

    suspend fun saveItem(item: ItemsEntity?) {
        if (item != null) {
            itemsRepository.insertItem(item)
            Log.e(GetCustomersUseCase.TAG, "customer ${item.iCode} inserted")
        }
    }
}
