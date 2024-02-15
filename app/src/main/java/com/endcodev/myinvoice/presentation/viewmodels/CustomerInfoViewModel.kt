package com.endcodev.myinvoice.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.database.entities.CustomersEntity
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.customer.CustomerUiState
import com.endcodev.myinvoice.domain.usecases.GetSimpleCustomerUseCase
import com.endcodev.myinvoice.domain.utils.App
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

    private val _uiState = MutableStateFlow(CustomerUiState())
    val uiState: StateFlow<CustomerUiState> = _uiState.asStateFlow()

    fun getCustomer(customerId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val customer = getSimpleCustomerUseCase.invoke(customerId)
                withContext(Dispatchers.Main) {
                    if (customer != null)
                        updateUi(customer)
                }
            } catch (e: Exception) {
                Log.e(App.tag, e.message.toString())
            }
        }
    }

    private fun updateUi(customer: Customer) {
        _uiState.update { currentState ->
            currentState.copy(
                id = customer.id,
                fiscalName = customer.fiscalName,
                telephone = customer.telephone,
                country = customer.country,
                image = customer.image,
                isLoading = false,
                isSaveEnabled = enableAccept(customer.id, customer.fiscalName),
                isDeleteEnabled = true
            )
        }
    }

    fun onDataChanged(
        identifier: String,
        fiscalName: String,
        telephone: String,
        country: String,
        email: String
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                id = identifier,
                fiscalName = fiscalName,
                telephone = telephone,
                country = country,
                email = email,
                isSaveEnabled = enableAccept(identifier, fiscalName)
            )
        }
    }

    private fun enableAccept(identifier: String, fiscalName: String) =
        identifier.isNotEmpty() && fiscalName.isNotEmpty()

    fun saveCustomer() {

        viewModelScope.launch {
            val uiState = _uiState.value
            val imageValue = uiState.image?.toString() ?: ""

            val customer = CustomersEntity(
                image = imageValue,
                id = uiState.id,
                fiscalName = uiState.fiscalName,
                telephone = uiState.telephone,
                country = uiState.country
            )
            getSimpleCustomerUseCase.saveCustomer(customer)
        }
    }

    fun updateUri(uri: Uri?) {
        _uiState.update { currentState ->
            currentState.copy(
                image = uri
            )
        }
    }

    fun deleteCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSimpleCustomerUseCase.deleteCustomer(_uiState.value.id)
            } catch (e: Exception) {
                Log.e(App.tag, e.message.toString())
            }
        }
    }
}

