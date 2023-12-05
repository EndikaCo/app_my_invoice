package com.endcodev.myinvoice.domain.models.product

import android.net.Uri

data class ProductUiState(
    val iImage : Uri? = null,
    val iCode : String = "",
    val iName : String = "",
    val iType : String = "",
    val iCost : Float = 0f,
    val iPrice : Float = 0f,
    val iStock: Float = 0F,
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false
) {
}