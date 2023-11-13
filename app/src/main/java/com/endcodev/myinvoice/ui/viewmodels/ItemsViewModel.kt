package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.model.FilterModel
import com.endcodev.myinvoice.data.model.ItemsListUiState
import com.endcodev.myinvoice.domain.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemsListUiState())
    val uiState: StateFlow<ItemsListUiState> = _uiState.asStateFlow()

    private suspend fun getItems(searchText: String) {
        _uiState.update { it.copy(isLoading = true) }
        delay(500L) // simulate network delay
        val items = getItemsUseCase.invoke() // get items from use case
        val filteredItems = if (searchText.isBlank()) {
            items
        } else {
            items.filter { it.doesMatchSearchQuery(searchText) }
        }
        _uiState.update { it.copy(itemsList = filteredItems, isLoading = false) }
    }

    fun setSearchText(searchText: String) {
        _uiState.update { it.copy(searchText = searchText) }
    }


    fun changeFilters(filters : List<FilterModel>) {
        _uiState.update { it.copy(filters = filters) }
    }

    init {
        viewModelScope.launch {
            uiState.collect { state ->
                getItems(state.searchText)
            }
        }
    }
}