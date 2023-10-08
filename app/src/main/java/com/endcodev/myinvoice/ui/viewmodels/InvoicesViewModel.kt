package com.endcodev.myinvoice.ui.viewmodels

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
import com.endcodev.myinvoice.data.model.InvoiceModel
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
class InvoicesViewModel: ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allInvoices)
    val invoices = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_persons) { text, invoices ->
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
            _persons.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

private val allInvoices = listOf(
    InvoiceModel(
        iId = 1,
        iCustomer = "Lackner"
    ),
    InvoiceModel(
        iId = 2,
        iCustomer = "Jezos"
    ),
    InvoiceModel(
        iId = 3,
        iCustomer =  "Bacon"
    ),
    InvoiceModel(
        iId = 4,
        iCustomer =  "Stops"
    )
)