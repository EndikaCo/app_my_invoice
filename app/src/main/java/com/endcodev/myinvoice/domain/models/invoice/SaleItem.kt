package com.endcodev.myinvoice.domain.models.invoice

import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.data.database.entities.SaleEntity
import com.endcodev.myinvoice.domain.models.product.Product

data class SaleItem(
    val id: Int = 0,
    val product: Product,
    val price: Float = 0f,
    val quantity: Float = 1f,
    val discount: Int = 0,
    val tax: Int = 0,
) {
    fun toEntity(): SaleEntity? {
        return Converters().productEntityToJson(product.toEntity())?.let { productJson ->
            SaleEntity(
                id = id,
                product = productJson,
                price = price,
                quantity = quantity,
                discount = discount,
                tax = tax,
            )
        }
    }
}