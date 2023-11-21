package com.endcodev.myinvoice.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.database.CustomersEntity
import com.endcodev.myinvoice.data.database.InvoicesEntity
import com.endcodev.myinvoice.data.model.InvoicesModel
import com.endcodev.myinvoice.data.repository.CustomersRepository
import com.endcodev.myinvoice.data.repository.InvoicesRepository
import javax.inject.Inject

class GetSimpleInvoiceUseCase @Inject constructor(
    private val repository : InvoicesRepository
) : ViewModel() {

    companion object {
        const val TAG = "GetSimpleCustomerUseCase"
    }

    suspend operator fun invoke(invoiceId: String?): InvoicesModel? {

        var invoice : InvoicesModel? = null

        if(invoiceId == null)
            return null

        try {
            invoice = repository.getInvoiceById(invoiceId)
        } catch (e: Exception) {
            Log.e(TAG, "invoice not found, $e")
        }
        return invoice
    }


    suspend fun saveInvoice(invoice: InvoicesEntity?) {
        if (invoice != null) {
            repository.insertInvoice(invoice)
            Log.v(GetCustomersUseCase.TAG, "customer ${invoice.iId} inserted")
        }
    }

    suspend fun updateInvoice(invoice: InvoicesEntity?) {
        if (invoice != null) {
            repository.updateInvoice(invoice)
            Log.v(GetCustomersUseCase.TAG, "customer ${invoice.iId} updated")
        }
    }
}