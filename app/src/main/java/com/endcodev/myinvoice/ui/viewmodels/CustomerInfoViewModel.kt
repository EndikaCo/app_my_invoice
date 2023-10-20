package com.endcodev.myinvoice.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.model.CustomerUiState
import com.endcodev.myinvoice.domain.GetCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerInfoViewModel @Inject constructor(
    private val getCustomersUseCase: GetCustomersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomerUiState())
    val uiState: StateFlow<CustomerUiState> = _uiState.asStateFlow()

    fun onDataChanged(identifier: String, fiscalName: String, telephone: String) {
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

    fun saveCustomer() {

        viewModelScope.launch {
            with(_uiState.value) {

                val customer = CustomersEntity(
                    cImage = cImage.toString(),
                    cIdentifier = cIdentifier,
                    cFiscalName = cFiscalName,
                    cTelephone = cTelephone
                )
                getCustomersUseCase.saveCustomer(customer)
            }
        }
    }

    fun updateUri(uri: Uri?) {
        _uiState.update { currentState ->
            currentState.copy(
                cImage = uri
            )
        }
    }

    fun getCustomerByName(customerIdentifier: String?) {

    }
}

