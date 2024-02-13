package com.endcodev.myinvoice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.invoice.InvoiceUiState
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.product.Product
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
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    fun setCustomer(customer: Customer) {
        _uiState.update {
            it.copy(
                invoice = it.invoice.copy(customer = customer)
            )
        }
    }

    fun setDate(date: String) {
        _uiState.update {
            it.copy(invoice = it.invoice.copy(date = date ) )
        }
    }

    fun getInvoice(invoiceId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val invoice = getSimpleInvoiceUseCase.invoke(invoiceId)
            withContext(Dispatchers.Main) {
                if (invoice != null)
                    updateUi(invoice)
                else
                    updateUi(Invoice(customer = Customer(image = null, fiscalName = "Select Customer", id = "0")))
            }
        }
    }

    private fun updateUi(invoice: Invoice) {
        _uiState.update { currentState ->
            currentState.copy(
                invoice = invoice,
            )
        }
    }

    fun saveInvoice() {
        viewModelScope.launch {
                _uiState.value.invoice.let { getSimpleInvoiceUseCase.saveInvoice(it) }
           //else
           ///     getSimpleInvoiceUseCase.updateInvoice(InvoicesModel(iCustomer = uiState.value.customer))
        }
    }

    fun deleteInvoice() {

    }

    fun addSale(sale: SaleItem) {
        _uiState.update { currentState ->
            val currentSaleList = currentState.invoice.saleList
            val existingSaleItemIndex = currentSaleList.indexOfFirst { it.id == sale.id }

            val newSaleList : MutableList<SaleItem> = if (existingSaleItemIndex != -1) {
                // Update the existing SaleItem
                currentSaleList.toMutableList().apply {
                    this[existingSaleItemIndex] = sale
                }
            } else {
                // Add the new SaleItem
                currentSaleList.plus(sale).toMutableList()
            }

            currentState.copy(
                invoice = currentState.invoice.copy(saleList = newSaleList)
            )
        }
    }

    fun setSaleProduct(product: Product) {
        _uiState.value.invoice.saleList
    }

    fun deleteSale(it: SaleItem) {
        _uiState.update { currentState ->
            val newSaleList = currentState.invoice.saleList.toMutableList().apply {
                remove(it)
            }
            currentState.copy(
                invoice = currentState.invoice.copy(saleList = newSaleList)
            )
        }
    }
}
