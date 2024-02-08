package com.endcodev.myinvoice.domain.models.product

import android.net.Uri
import com.endcodev.myinvoice.data.database.entities.ItemsEntity

data class Product(
    val iImage: Uri?,
    val iCode: String,
    val iName: String,
    val iDescription: String,
    val iType: String,
    val iPrice: Float,
    val iCost: Float,
    val iStock: Float,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            iCode,
            iName,
            iDescription,
            iType,
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

    fun toEntity(): ItemsEntity {
        return ItemsEntity(
            iImage = iImage.toString(),
            iCode = iCode,
            iName = iName,
            iDescription = iDescription,
            iType = iType,
            iPrice = iPrice,
            iCost = iCost,
            iStock = iStock,
        )
    }
}
