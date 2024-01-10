package com.endcodev.myinvoice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.domain.usecases.GetInvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val getInvoicesUseCase: GetInvoicesUseCase
): ViewModel(
) {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _invoices = MutableStateFlow(emptyList<Invoice>())
    val invoices = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_invoices) { text, invoices ->
            if(text.isBlank()) {
                invoices
            } else {
                delay(500L)
                invoices.filter { invoice ->
                    invoice.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _invoices.value
        )

    init {
        viewModelScope.launch {
            _isSearching.update { true }
            _invoices.value = getInvoicesUseCase.invoke().toMutableList()
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

