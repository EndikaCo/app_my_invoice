package com.endcodev.myinvoice.domain.models.customer

import android.net.Uri
import com.endcodev.myinvoice.data.database.entities.CustomersEntity

data class Customer(
    val image: Uri?,
    val id: String = "-",
    val fiscalName: String,
    val telephone: String = "",
    val country: String = "",
){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            id,
            fiscalName,
            telephone
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

    fun toEntity(): CustomersEntity {
        return CustomersEntity(
            image = image.toString(),
            id = id,
            fiscalName = fiscalName,
            telephone = telephone,
            country = country,
        )
    }
}