package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.domain.GetCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val getCustomersUseCase: GetCustomersUseCase,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _customers = MutableStateFlow(allCustomers)

    val customers = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_customers) { text, customers ->
            if(text.isBlank()) {
                customers
            } else {
                delay(500L)
                customers.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }

        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _customers.value
        )

    /*init {
        viewModelScope.launch {
            _isSearching.value = true
            val players = getPlayersUseCase.invoke()
            if (players != null) {
                _customers.value = players.toMutableList()
            }
            _isSearching.value = false
        }
    }*/

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

private val allCustomers = listOf(
    CustomerModel(1, "B95768523", "Volkwagermit", "+34623213213"),
    CustomerModel(2, "1608876623V", "Opel motors", "+86732132133"),
    CustomerModel(3, "A323145125212", "Bar Paco", "+51624223213")
)
