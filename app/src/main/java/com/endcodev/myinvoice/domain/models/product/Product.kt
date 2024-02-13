package com.endcodev.myinvoice.domain.models.product

import android.net.Uri
import com.endcodev.myinvoice.data.database.entities.ProductEntity

data class Product(
    val image: Uri?,
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val price: Float,
    val cost: Float,
    val stock: Float,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            id,
            name,
            description,
            type,
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

    fun toEntity(): ProductEntity {
        return ProductEntity(
            image = image.toString(),
            id = id,
            name = name,
            description = description,
            type = type,
            price = price,
            cost = cost,
            stock = stock,
        )
    }
}
