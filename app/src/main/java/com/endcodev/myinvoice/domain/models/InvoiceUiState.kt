package com.endcodev.myinvoice.domain.models

data class InvoiceUiState(
    val customer : CustomerModel? = null,
    val isLoading : Boolean = true,
    val id : String = "-",
    )
