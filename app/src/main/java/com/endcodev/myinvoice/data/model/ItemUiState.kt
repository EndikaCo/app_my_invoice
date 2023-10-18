package com.endcodev.myinvoice.data.model

data class ItemUiState(
    val iImage : Int? = null,
    val iCode : String = "",
    val iName : String = "",
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false
)


