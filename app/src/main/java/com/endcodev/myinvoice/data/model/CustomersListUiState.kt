package com.endcodev.myinvoice.data.model

data class CustomersListUiState(
    val searchText : String = "",
    val isLoading : Boolean = false,
    val customersList : List<CustomerModel> = emptyList(),
    val showDialog : Boolean = false,
    val filters : MutableList<FilterModel> = mutableListOf(FilterModel(FilterType.NEW, "New Filter"))
)