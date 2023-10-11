package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.data.model.CustomerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CustomerInfoViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomerUiState())
    val uiState: StateFlow<CustomerUiState> = _uiState.asStateFlow()

    fun onDataChanged(identifier: String,fiscalName: String, telephone: String ) {
        _uiState.update { currentState ->
            currentState.copy(
                cIdentifier = identifier,
                cFiscalName = fiscalName,
                cTelephone = telephone,
                isAcceptEnabled = enableAccept(identifier, fiscalName)
            )
        }
    }

    private fun enableAccept(identifier: String, fiscalName: String) =
        identifier.isNotEmpty() && fiscalName.isNotEmpty()
}