package com.endcodev.myinvoice.data.model

import android.net.Uri

data class ItemUiState(
    val iImage : Uri? = null,
    val iCode : String = "",
    val iName : String = "",
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false
)