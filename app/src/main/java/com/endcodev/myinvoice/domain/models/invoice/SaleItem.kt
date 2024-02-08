package com.endcodev.myinvoice.domain.models.invoice

import com.endcodev.myinvoice.data.converters.Converters
import com.endcodev.myinvoice.data.database.entities.SaleEntity
import com.endcodev.myinvoice.domain.models.product.Product

data class SaleItem(
    val sId: Int = 0,
    val sProduct: Product,
    val sPrice: Float = 0f,
    val sQuantity: Float = 1f,
    val sDiscount: Int = 0,
    val sTax: Int = 0,
) {
    fun toEntity(): SaleEntity? {
        return Converters().productEntityToJson(sProduct)?.let { productJson ->
            SaleEntity(
                sId = sId,
                sProduct = productJson,
                sPrice = sPrice,
                sQuantity = sQuantity,
                sDiscount = sDiscount,
                sTax = sTax,
            )
        }
    }
}