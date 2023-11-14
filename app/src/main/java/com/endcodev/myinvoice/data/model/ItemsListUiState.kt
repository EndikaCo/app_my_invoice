package com.endcodev.myinvoice.data.model

data class ItemsListUiState (
    val isLoading : Boolean = false,
    val itemsList : List<ItemModel> = emptyList(),
    val filters : List<FilterModel> = emptyList()
)
