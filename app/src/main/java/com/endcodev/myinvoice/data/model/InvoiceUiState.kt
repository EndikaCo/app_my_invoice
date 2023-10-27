package com.endcodev.myinvoice.data.model

data class InvoiceUiState(
    val customer : CustomerModel? = null,
    val customersList : List<CustomerModel>? = null,
    val showDialog : Boolean = false,
    val isLoading : Boolean = true,

    )
