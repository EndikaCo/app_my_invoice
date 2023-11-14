package com.endcodev.myinvoice.domain

import android.util.Log
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.database.ItemsEntity
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.ItemModel
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
