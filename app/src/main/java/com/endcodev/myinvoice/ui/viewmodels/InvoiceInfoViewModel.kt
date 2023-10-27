package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.data.model.InvoiceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InvoiceInfoViewModel @Inject constructor(

) : ViewModel(){
    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()
}
