package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.data.repository.InvoicesRepository
import javax.inject.Inject

class GetInvoicesUseCase @Inject constructor(
    private val invoicesRepository : InvoicesRepository
){

    companion object {
        const val TAG = "GetInvoicesUseCase"
    }

    suspend operator fun invoke(): List<Invoice>{
        var invoicesList: List<Invoice> = emptyList()

        try {
            invoicesList = invoicesRepository.getAllInvoicesFromDB()
        } catch (e: Exception){
            Log.e(TAG, "No items found, $e" )
        }
        return invoicesList
    }
}