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
import java.time.Instant.now
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class InvoiceInfoViewModel @Inject constructor(
    private val getSimpleInvoiceUseCase: GetSimpleInvoiceUseCase,
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    fun setCustomer(customer: CustomerModel) {
        _uiState.update {
            it.copy(
                invoicesModel = it.invoicesModel.copy(iCustomer = customer)
            )
        }
    }

    fun setDate(date: String) {
        _uiState.update {
            it.copy(invoicesModel = it.invoicesModel.copy(iDate = date ) )
        }
    }

    fun getInvoice(invoiceId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val invoice = getSimpleInvoiceUseCase.invoke(invoiceId)
            withContext(Dispatchers.Main) {
                if (invoice != null)
                    updateUi(invoice)
                else
                    updateUi(InvoicesModel(iCustomer = CustomerModel(cImage = null, cFiscalName = "Select Customer", cIdentifier = "0")))
            }
        }
    }

    private fun updateUi(invoice: InvoicesModel) {
        _uiState.update { currentState ->
            currentState.copy(
                invoicesModel = invoice,
            )
        }
    }

    fun saveInvoice() {
        viewModelScope.launch {
                _uiState.value.invoicesModel.let { getSimpleInvoiceUseCase.saveInvoice(it) }
           //else
           ///     getSimpleInvoiceUseCase.updateInvoice(InvoicesModel(iCustomer = uiState.value.customer))
        }
    }

    fun deleteInvoice() {

    }
}
