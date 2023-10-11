package com.endcodev.myinvoice.data.model

data class CustomerUiState (
    val cImage: Int? = null,
    val cIdentifier : String = "",
    val cFiscalName : String = "",
    val cTelephone : String = "",
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false,
    val errorMessage : String? = null,
)
