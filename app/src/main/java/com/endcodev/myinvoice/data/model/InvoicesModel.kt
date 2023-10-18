package com.endcodev.myinvoice.data.model

data class InvoicesModel(
    val iId: Int,
    val iCustomer: String
    )
{
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            iId.toString(),
            iCustomer,
            "${iCustomer.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}