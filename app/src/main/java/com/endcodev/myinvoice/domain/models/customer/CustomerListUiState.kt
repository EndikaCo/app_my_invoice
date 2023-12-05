package com.endcodev.myinvoice.domain.models.customer

import com.endcodev.myinvoice.domain.models.common.FilterModel

data class CustomerListUiState(
    val searchText : String = "",
    val isLoading : Boolean = false,
    val customersList : List<Customer> = emptyList(),
    val filters : List<FilterModel> = emptyList()
)
