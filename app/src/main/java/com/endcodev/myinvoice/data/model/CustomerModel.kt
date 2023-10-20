package com.endcodev.myinvoice.data.model

import android.net.Uri

data class CustomerModel(
    val cImage: Uri?,
    val cIdentifier: String,
    val cFiscalName: String,
    val cTelephone: String
){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            cIdentifier,
            cFiscalName,
            cTelephone
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}