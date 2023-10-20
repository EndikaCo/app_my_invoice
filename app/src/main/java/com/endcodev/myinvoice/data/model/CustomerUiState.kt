package com.endcodev.myinvoice.data.model

import android.net.Uri

data class CustomerUiState (
    val cImage: Uri? = null,
    val cIdentifier : String = "",
    val cFiscalName : String = "",
    val cTelephone : String = "",
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false,
    val errorMessage : String? = null,
)
