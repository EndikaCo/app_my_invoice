package com.endcodev.myinvoice.domain.models

import com.endcodev.myinvoice.data.Converters
import com.endcodev.myinvoice.data.database.InvoicesEntity

data class InvoicesModel(
    val iId: Int? = null,
    val iCustomer: CustomerModel,
    )
{
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            iId.toString(),
            iCustomer.cFiscalName,
            "${iCustomer.cFiscalName.first()}", //todo should be name also
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true) ?: false
        }
    }

    fun toEntity(): InvoicesEntity {
        return InvoicesEntity(
            iId = iId ?: 0,
            iCustomer = Converters().customerToJson(iCustomer.toEntity()) ?: "poronga",
        )
    }
}