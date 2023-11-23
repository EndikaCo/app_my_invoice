package com.endcodev.myinvoice.domain.models

data class ItemsListUiState (
    val isLoading : Boolean = false,
    val itemsList : List<ItemModel> = emptyList(),
    val filters : List<FilterModel> = emptyList()
)
