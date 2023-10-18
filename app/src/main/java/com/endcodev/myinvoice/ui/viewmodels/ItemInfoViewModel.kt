package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.data.model.ItemUiState
import com.endcodev.myinvoice.domain.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ItemUiState())
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    fun onDataChanged(code: String, name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                iName = name,
                iCode = code,
                isAcceptEnabled = enableAccept(code, name)
            )
        }
    }

    private fun enableAccept(code: String, name: String) =
        code.isNotEmpty() && name.isNotEmpty()
}