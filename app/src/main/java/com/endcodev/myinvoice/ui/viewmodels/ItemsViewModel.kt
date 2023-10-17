package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.model.ItemsModel
import com.endcodev.myinvoice.domain.GetCustomersUseCase
import com.endcodev.myinvoice.domain.GetItemsUseCase
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
    ) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _items = MutableStateFlow(emptyList<ItemsModel>())

    @OptIn(FlowPreview::class)
    val items = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_items) { text, item ->
            if(text.isBlank()) {
                item
            } else {
                delay(500L)
                item.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }

        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            _isSearching.value = true
            val items = getItemsUseCase.invoke()
            if (items != null) {
                _items.value = items.toMutableList()
            }
            _isSearching.value = false
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}