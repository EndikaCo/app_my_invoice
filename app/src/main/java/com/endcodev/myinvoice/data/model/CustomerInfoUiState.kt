package com.endcodev.myinvoice.data.model

import android.net.Uri

data class CustomerInfoUiState (
    val cImage: Uri? = null,
    val cIdentifier : String = "",
    val cFiscalName : String = "",
    val cTelephone : String = "",
    val cEmail : String = "",
    val cCountry : String = "",
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false,
    val errorMessage : String? = null,
)
