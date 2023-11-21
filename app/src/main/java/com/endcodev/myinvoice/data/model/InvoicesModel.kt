package com.endcodev.myinvoice.data.model

data class InvoicesModel(
    val iId: Int? = null,
    val iCustomerId: String
    )
{
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            iId.toString(),
            iCustomerId,
            "${iCustomerId.first()}", //todo should be name also
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}