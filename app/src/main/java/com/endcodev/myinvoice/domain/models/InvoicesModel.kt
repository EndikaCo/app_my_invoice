package com.endcodev.myinvoice.domain.models

import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.data.database.entities.InvoicesEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class InvoicesModel(
    val iId: Int? = null,
    val iDate : String = getDate(),//todo
    val iCustomer: CustomerModel,
    val iReference: String = "",
    val iTotal: Float = 0f,
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
            iCustomer = Converters().customerEntityToJson(iCustomer.toEntity()) ?: "error",
            iDate = iDate,
        )
    }
}