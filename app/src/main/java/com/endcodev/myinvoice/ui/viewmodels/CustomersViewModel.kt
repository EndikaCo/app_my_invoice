package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.domain.models.CustomersListUiState
import com.endcodev.myinvoice.domain.models.FilterModel
import com.endcodev.myinvoice.domain.usecases.GetCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val getCustomersUseCase: GetCustomersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomersListUiState())
    val uiState: StateFlow<CustomersListUiState> = _uiState.asStateFlow()

    private suspend fun getCustomers(searchText: String) {
        _uiState.update { it.copy(isLoading = true) }
        delay(500L) // simulate network delay
        val customers = getCustomersUseCase.invoke() // get customers from use case
        val filteredCustomers = if (searchText.isBlank()) {
            customers
        } else {
            customers.filter { it.doesMatchSearchQuery(searchText) }
        }
        _uiState.update { it.copy(customersList = filteredCustomers, isLoading = false) }
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
                getCustomers(state.searchText)
            }
        }
    }
}