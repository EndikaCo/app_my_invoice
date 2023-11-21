package com.endcodev.myinvoice.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.database.InvoicesEntity
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.InvoiceUiState
import com.endcodev.myinvoice.data.model.InvoicesModel
import com.endcodev.myinvoice.domain.GetSimpleCustomerUseCase
import com.endcodev.myinvoice.domain.GetSimpleInvoiceUseCase
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
            val customer = getSimpleCustomerUseCase.invoke(invoice?.iCustomerId)
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

            if (uiState.value.id.isNotEmpty() && uiState.value.id != "-")
                getSimpleInvoiceUseCase.saveInvoice(
                    InvoicesEntity(
                        iCustomer = uiState.value.customer!!.cIdentifier,
                        iId = uiState.value.id.toInt()
                    )
                )

            if (uiState.value.customer != null && uiState.value.customer!!.cFiscalName.isNotEmpty())
                getSimpleInvoiceUseCase.saveInvoice(
                    InvoicesEntity(iCustomer = uiState.value.customer!!.cIdentifier)
                )
        }
    }
}
