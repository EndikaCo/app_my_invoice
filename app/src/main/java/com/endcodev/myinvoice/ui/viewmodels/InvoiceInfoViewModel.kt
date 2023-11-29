package com.endcodev.myinvoice.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.domain.models.CustomerModel
import com.endcodev.myinvoice.domain.models.InvoiceUiState
import com.endcodev.myinvoice.domain.models.InvoicesModel
import com.endcodev.myinvoice.domain.usecases.GetSimpleCustomerUseCase
import com.endcodev.myinvoice.domain.usecases.GetSimpleInvoiceUseCase
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
class InvoiceInfoViewModel @Inject constructor(
    private val getSimpleInvoiceUseCase: GetSimpleInvoiceUseCase,
    private val getSimpleCustomerUseCase: GetSimpleCustomerUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    fun setCustomer(customer: CustomerModel) {
        _uiState.update {
            it.copy(customer = customer)
        }
    }

    fun getInvoice(invoiceId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.v("invoiceId", invoiceId.toString())
            val invoice = getSimpleInvoiceUseCase.invoke(invoiceId)
            Log.v("invoice", invoice.toString())
            val customer = getSimpleCustomerUseCase.invoke(invoice?.iCustomer?.cIdentifier)
            Log.v("customer", customer.toString())
            withContext(Dispatchers.Main) {
                if (invoice != null && customer != null)
                    updateUi(invoice, customer)
            }
        }
    }

    private fun updateUi(invoice: InvoicesModel, customer: CustomerModel) {
        _uiState.update { currentState ->
            currentState.copy(
                customer = customer,
                id = invoice.iId.toString(),
                isLoading = false,
            )
        }
    }

    fun saveInvoice() {
        viewModelScope.launch {
        Log.v("invoice", uiState.value.id)
            //if (uiState.value.id == "-")
            uiState.value.customer?.let { InvoicesModel(iCustomer = it) }
                ?.let { getSimpleInvoiceUseCase.saveInvoice(it) }

           //else
           ///     getSimpleInvoiceUseCase.updateInvoice(InvoicesModel(iCustomer = uiState.value.customer))
        }
    }

    fun deleteInvoice() {

    }
}
