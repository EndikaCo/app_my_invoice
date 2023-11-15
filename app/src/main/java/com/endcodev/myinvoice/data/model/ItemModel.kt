package com.endcodev.myinvoice.data.model

import android.net.Uri

class ItemModel(
    val iImage: Uri?,
    val iCode: String,
    val iName: String,
    val iDescription: String,
    val iType: String,
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
