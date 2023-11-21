package com.endcodev.myinvoice.data.model

data class InvoiceUiState(
    val customer : CustomerModel? = null,
    val isLoading : Boolean = true,
    val id : String = "-",
    )
