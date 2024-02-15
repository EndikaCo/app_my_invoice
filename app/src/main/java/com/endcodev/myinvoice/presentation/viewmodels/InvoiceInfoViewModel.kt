package com.endcodev.myinvoice.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.domain.models.invoice.InvoiceUiState
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.usecases.GetSimpleInvoiceUseCase
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
class InvoiceInfoViewModel @Inject constructor(
    private val getSimpleInvoiceUseCase: GetSimpleInvoiceUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    fun getInvoice(invoiceId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val invoice = getSimpleInvoiceUseCase.invoke(invoiceId)
                withContext(Dispatchers.Main) {
                    if (invoice != null)
                        updateUi(invoice)
                }
            } catch (e: Exception) {
                Log.e(App.tag, e.message.toString())
            }
        }
    }

    fun updateCustomer(customer: Customer) {
        _uiState.update {
            it.copy(
                invoice = it.invoice.copy(customer = customer),
                isSaveEnabled = enableAccept(it.invoice.customer),
            )
        }
    }

    fun setDate(date: String) {
        _uiState.update {
            it.copy(invoice = it.invoice.copy(date = date))
        }
    }

    private fun updateUi(invoice: Invoice) {
        _uiState.update { currentState ->
            currentState.copy(
                invoice = invoice,
                isSaveEnabled = enableAccept(invoice.customer),
                isDeleteEnabled = true
            )
        }
    }

    fun saveInvoice() {
        viewModelScope.launch {
            _uiState.value.invoice.let { getSimpleInvoiceUseCase.saveInvoice(it) }
        }
    }

    fun deleteInvoice() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSimpleInvoiceUseCase.deleteInvoice(uiState.value.invoice.id)
            } catch (e: Exception) {
                Log.e(App.tag, "${e.message}")
            }
        }
    }

    private fun enableAccept(customer: Customer) = //todo
        customer.id != "-"

    fun addSale(sale: SaleItem) {
        _uiState.update { currentState ->
            val currentSaleList = currentState.invoice.saleList
            val existingSaleItemIndex = currentSaleList.indexOfFirst {
                it.id == sale.id
            }

            val newSaleList: MutableList<SaleItem> = if (existingSaleItemIndex != -1) {
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
