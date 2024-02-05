package com.endcodev.myinvoice.domain.models.invoice

import com.endcodev.myinvoice.domain.models.product.Product

data class SaleItem(
    val sId: Int = 0,
    val sProduct: Product,
    val sPrice: Float = 0f,
    val sQuantity: Float = 1f,
    val sDiscount : Int = 0,
    val sTax : Int = 0,
    )