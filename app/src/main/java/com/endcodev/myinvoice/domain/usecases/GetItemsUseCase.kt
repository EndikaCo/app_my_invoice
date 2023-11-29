package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.entities.ItemsEntity
import com.endcodev.myinvoice.data.repository.ItemsRepository
import com.endcodev.myinvoice.domain.models.ItemModel
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
                    iCode = "BFP420E6327",
                    iDescription = "40V 800mA 300MHz",
                    iName = "Transistor 2N2222A",
                    iImage = null,
                    iType = "Transistor NPN"
                ),
                ItemsEntity(
                    iCode = "2449-A61C",
                    iDescription = "Relay automotive SPDT 30A 12V",
                    iName = "30A 12V Relay",
                    iImage = null,
                    iType = "12VCC Relay"
                ),
                ItemsEntity(
                    iCode = "PRC-231DDD",
                    iDescription = "230VAC 8W 90CRI 2700K",
                    iName = "Led GU10 2K7",
                    iImage = null,
                    iType = "AC Led Lamp"
                ),
            )
        }
    }
}