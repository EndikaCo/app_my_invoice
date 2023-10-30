package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.CustomersListUiState
import com.endcodev.myinvoice.domain.GetCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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

    fun manageDialog(dialogId : Int) {
        if (dialogId == 0){
            if(!uiState.value.showDialog)
                _uiState.update { it.copy(showDialog = true) }
            else
                _uiState.update { it.copy(showDialog = false) }
        }
    }

    init {
        viewModelScope.launch {
            uiState.collect { state ->
                getCustomers(state.searchText)
            }
        }
    }
}