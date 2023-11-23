package com.endcodev.myinvoice.domain.models

data class CustomersListUiState(
    val searchText : String = "",
    val isLoading : Boolean = false,
    val customersList : List<CustomerModel> = emptyList(),
    val filters : List<FilterModel> = emptyList()
)
