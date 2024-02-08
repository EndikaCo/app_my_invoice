package com.endcodev.myinvoice.domain.models.customer

import android.net.Uri
import com.endcodev.myinvoice.data.database.entities.CustomersEntity

data class Customer(
    val cImage: Uri?,
    val cIdentifier: String = "-",
    val cFiscalName: String,
    val cTelephone: String = "",
    val cCountry: String = "",
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

    fun toEntity(): CustomersEntity {
        return CustomersEntity(
            cImage = cImage.toString(),
            cIdentifier = cIdentifier,
            cFiscalName = cFiscalName,
            cTelephone = cTelephone,
            cCountry = cCountry,
        )
    }
}