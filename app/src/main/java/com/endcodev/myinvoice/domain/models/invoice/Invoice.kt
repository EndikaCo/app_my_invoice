package com.endcodev.myinvoice.domain.models.invoice

import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.data.database.entities.InvoicesEntity
import com.endcodev.myinvoice.domain.models.customer.Customer

data class Invoice(
    val id: Int? = null,
    val date: String = getDate(),
    val customer: Customer,
    val reference: String = "",
    val saleList: MutableList<SaleItem> = mutableListOf(),
) {
    val iSubtotal: Float
        get() = saleList.sumOf { it.price.toDouble() * it.quantity }.toFloat()

    val iTaxes: Float
        get() = saleList.sumOf { it.tax.toDouble() * it.quantity }.toFloat()

    val iDiscount: Float
        get() = saleList.sumOf { it.discount.toDouble() * it.quantity }.toFloat()

    val iTotal: Float
        get() = iSubtotal + iTaxes - iDiscount

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            id.toString(),
            customer.fiscalName,
            "${customer.fiscalName.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

    fun toEntity(): InvoicesEntity {
        return InvoicesEntity(
            id = id ?: 0,
            customer = Converters().customerEntityToJson(customer.toEntity()) ?: "error",
            date = date,
            saleList = Converters().salesEntityToJson(saleList)
        )
    }

}