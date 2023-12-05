package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.data.repository.InvoicesRepository
import javax.inject.Inject

class GetSimpleInvoiceUseCase @Inject constructor(
    private val repository : InvoicesRepository
) : ViewModel() {

    companion object {
        const val TAG = "GetSimpleCustomerUseCase"
    }

    operator fun invoke(invoiceId: String?): Invoice? {

        var invoice : Invoice? = null

        if(invoiceId == null)
            return null

        try {
            invoice = repository.getInvoiceById(invoiceId)
        } catch (e: Exception) {
            Log.e(TAG, "invoice not found, $e")
        }
        return invoice
    }


    suspend fun saveInvoice(invoice: Invoice) {
            repository.insertInvoice(invoice)
            Log.v(GetCustomersUseCase.TAG, "customer ${invoice.iId} inserted")
    }

    suspend fun updateInvoice(invoice: Invoice) {
            repository.updateInvoice(invoice)
            Log.v(GetCustomersUseCase.TAG, "customer ${invoice.iId} updated")

    }
}