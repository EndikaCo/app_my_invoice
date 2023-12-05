package com.endcodev.myinvoice.domain.models.product

import com.endcodev.myinvoice.domain.models.common.FilterModel

data class ProductListUiState (
    val searchText : String = "",
    val isLoading : Boolean = false,
    val itemsList : List<Product> = emptyList(),
    val filters : List<FilterModel> = emptyList()
)
