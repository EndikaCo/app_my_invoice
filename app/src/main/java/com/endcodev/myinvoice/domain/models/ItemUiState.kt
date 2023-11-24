package com.endcodev.myinvoice.domain.models

import android.net.Uri

data class ItemUiState(
    val iImage : Uri? = null,
    val iCode : String = "",
    val iName : String = "",
    val iType : String = "",
    val iCost : Float = 0f,
    val iPrice : Float = 0f,
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false
) {
}