package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.database.InvoicesEntity
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.InvoiceUiState
import com.endcodev.myinvoice.domain.GetCustomersUseCase
import com.endcodev.myinvoice.domain.GetSimpleInvoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceInfoViewModel @Inject constructor(
    private val getSimpleInvoiceUseCase: GetSimpleInvoiceUseCase,
    ) : ViewModel(){

    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    fun setCustomer(customer: CustomerModel) {
        _uiState.update {
            it.copy(customer = customer)
        }
    }

    fun saveInvoice() {
        viewModelScope.launch {
            if (uiState.value.customer != null && uiState.value.customer!!.cFiscalName.isNotEmpty())
                getSimpleInvoiceUseCase.saveInvoice(InvoicesEntity(iCustomer = uiState.value.customer!!.cFiscalName)) //todo
            // todo avoid same id
        }
    }

}
