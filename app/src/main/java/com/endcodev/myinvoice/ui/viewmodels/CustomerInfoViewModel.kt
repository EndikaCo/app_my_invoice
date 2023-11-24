package com.endcodev.myinvoice.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.domain.models.CustomerModel
import com.endcodev.myinvoice.domain.models.CustomerInfoUiState
import com.endcodev.myinvoice.domain.usecases.GetSimpleCustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerInfoViewModel @Inject constructor(
    private val getSimpleCustomerUseCase: GetSimpleCustomerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomerInfoUiState())
    val uiState: StateFlow<CustomerInfoUiState> = _uiState.asStateFlow()

    fun getCustomer(customerId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val customer = getSimpleCustomerUseCase.invoke(customerId)
            withContext(Dispatchers.Main) {
                if (customer != null)
                    updateUi(customer)
            }
        }
    }

    private fun updateUi(customer: CustomerModel) {
        _uiState.update { currentState ->
            currentState.copy(
                cIdentifier = customer.cIdentifier,
                cFiscalName = customer.cFiscalName,
                cTelephone = customer.cTelephone,
                cCountry = customer.cCountry,
                cImage = customer.cImage,
                isLoading = false,
                isAcceptEnabled = enableAccept(customer.cIdentifier, customer.cFiscalName)
            )
        }
    }


    fun onDataChanged(identifier: String, fiscalName: String, telephone: String, country: String, email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                cIdentifier = identifier,
                cFiscalName = fiscalName,
                cTelephone = telephone,
                cCountry = country,
                cEmail = email,
                isAcceptEnabled = enableAccept(identifier, fiscalName))
        }
    }

    private fun enableAccept(identifier: String, fiscalName: String) =
        identifier.isNotEmpty() && fiscalName.isNotEmpty()


    fun saveCustomer( ) {
        viewModelScope.launch {
            with(_uiState.value) {
                val customer = CustomersEntity(
                    cImage = cImage.toString(),
                    cIdentifier = cIdentifier,
                    cFiscalName = cFiscalName,
                    cTelephone = cTelephone,
                    cCountry = cCountry
                )
                getSimpleCustomerUseCase.saveCustomer(customer)
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
}

