package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.entities.ItemsEntity
import com.endcodev.myinvoice.domain.models.ItemModel
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
                    iCode = "PRO-2345", iDescription = " 230V 3000K CRI85", iName = "Gu10 LED Lamp", iImage = null,
                    iType = "LED Lamp"
                ),
                ItemsEntity(
                    iCode = "HK-2356", iDescription = "Installation of electric device in plant", iName = "Installation plant", iImage = null,
                    iType = "Service"
                ),
                ItemsEntity(
                    iCode = "2321513", iDescription = "Wire to small voltage instalations", iName = "2x1mm Wire red and black", iImage = null,
                    iType = "Wires"
                ),
            )
        }
    }
}