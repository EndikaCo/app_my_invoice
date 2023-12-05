package com.endcodev.myinvoice.domain.models.customer

import android.net.Uri

data class CustomerUiState (
    val cImage: Uri? = null, //todo use the CustomerModel abd isLoading etc appart?
    val cIdentifier : String = "",
    val cFiscalName : String = "",
    val cTelephone : String = "",
    val cEmail : String = "",
    val cCountry : String = "",
    val isLoading : Boolean = false,
    val isAcceptEnabled : Boolean = false,
    val errorMessage : String? = null,
)
