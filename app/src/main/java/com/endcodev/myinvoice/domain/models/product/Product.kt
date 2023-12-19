package com.endcodev.myinvoice.domain.models.product

import android.net.Uri

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
}
