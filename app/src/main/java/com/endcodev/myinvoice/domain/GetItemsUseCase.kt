package com.endcodev.myinvoice.domain

import android.util.Log
import com.endcodev.myinvoice.data.database.ItemsEntity
import com.endcodev.myinvoice.data.model.ItemModel
import com.endcodev.myinvoice.data.repository.ItemsRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository
) {

    suspend operator fun invoke(): List<ItemModel> {
        var itemsList: List<ItemModel>? = null

        try {
            itemsList = itemsRepository.getAllItemsFromDB()
        } catch (e: Exception) {
            Log.e(TAG, "No items found, $e")
        }
        if (itemsList.isNullOrEmpty()) {
            Log.v(TAG, "null items list")
            itemsRepository.insertAllItems(exampleCustomers())
            itemsList = itemsRepository.getAllItemsFromDB()
        }
        return itemsList
    }

    companion object {
        const val TAG = "GetItemsUseCase"

        fun exampleCustomers(): List<ItemsEntity> {
            return arrayListOf(
                ItemsEntity(
                    iCode = "1", iDescription = "example1", iName = "example1", iImage = null,
                    iType = "type"
                ),
                ItemsEntity(
                    iCode = "2", iDescription = "example2", iName = "example3", iImage = null,
                    iType = "type"
                ),
                ItemsEntity(
                    iCode = "3", iDescription = "example2", iName = "example3", iImage = null,
                    iType = "type"
                ),
            )
        }
    }
}