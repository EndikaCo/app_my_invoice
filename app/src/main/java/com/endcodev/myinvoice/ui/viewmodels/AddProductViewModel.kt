package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.domain.models.product.ProductListUiState
import com.endcodev.myinvoice.domain.usecases.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val getProductsUseCase: GetItemsUseCase,
    ) : ViewModel(){

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            uiState.collect { state ->
                getItems(state.searchText)
            }
        }
    }

    private suspend fun getItems(searchText: String) {
        _uiState.update { it.copy(isLoading = true) }
        delay(500L)
        val products = getProductsUseCase.invoke()
        val filteredItems = if (searchText.isBlank()) {
            products
        } else {
            products.filter { it.doesMatchSearchQuery(searchText) }
        }
        _uiState.update { it.copy(itemsList = filteredItems, isLoading = false) }
    }

    fun setSearchText(searchText: String) {
        _uiState.update { it.copy(searchText = searchText) }
    }
}
