package com.endcodev.myinvoice.domain.models


data class ItemSaleModel(
    val sProduct: ItemModel,
    val sPrice: Float = 0f,
    val sQuantity: Float = 0f,
    val sDiscount : Int = 0,
)