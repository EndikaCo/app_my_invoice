package com.endcodev.myinvoice.domain.models.invoice

import com.endcodev.myinvoice.domain.models.product.Product


data class SaleItem(
    val sProduct: Product,
    val sPrice: Float = 0f,
    val sQuantity: Float = 0f,
    val sDiscount : Int = 0,
)