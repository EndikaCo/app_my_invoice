package com.endcodev.myinvoice.domain.models.customer

import android.net.Uri

data class CustomerUiState (
    val image: Uri? = null,
    val id : String = "",
    val fiscalName : String = "",
    val telephone : String = "",
    val email : String = "",
    val country : String = "",
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val isSaveEnabled : Boolean = false,
    val isDeleteEnabled: Boolean = false
)
