package com.endcodev.myinvoice.data

data class CustomerModel(
    val cId : Int,
    val cImage: Int?,
    val cIdentifier : String,
    val cFiscalName : String,
    val cTelephone : String
){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$cId",
            cIdentifier,
            cFiscalName,
            cTelephone
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}