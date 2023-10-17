package com.endcodev.myinvoice.data.model

class ItemsModel (
    val iImage: Int?,
    val iCode : String,
    val iName : String,
    val iDescription : String,
)
{
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            iCode,
            iName,
            iDescription
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
