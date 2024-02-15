package com.endcodev.myinvoice.domain.models.product

import android.net.Uri

data class ProductUiState(
    val image : Uri? = null,
    val id : String = "",
    val name : String = "",
    val type : String = "",
    val cost : Float = 0f,
    val price : Float = 0f,
    val stock: Float = 0F,
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false,
    val isDeleteEnabled : Boolean = false
)