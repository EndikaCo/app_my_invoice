package com.endcodev.myinvoice.domain.usecases

import android.util.Log
import com.endcodev.myinvoice.data.database.InvoicesEntity
import com.endcodev.myinvoice.domain.models.InvoicesModel
import com.endcodev.myinvoice.data.repository.InvoicesRepository
import javax.inject.Inject

class GetInvoicesUseCase @Inject constructor(
    private val invoicesRepository : InvoicesRepository
){

    companion object {
        const val TAG = "GetInvoicesUseCase"
    }

    suspend operator fun invoke(): List<InvoicesModel>{
        var invoicesList: List<InvoicesModel> = emptyList()

        try {
            invoicesList = invoicesRepository.getAllInvoicesFromDB()
        } catch (e: Exception){
            Log.e(TAG, "No items found, $e" )
        }
        //if (invoicesList.isNullOrEmpty()){
            //invoicesRepository.insertAllItems(exampleInvoices())
            //invoicesList = invoicesRepository.getAllInvoicesFromDB()
        //}
        return invoicesList
    }



    private fun exampleInvoices(): MutableList<InvoicesEntity> {
        return arrayListOf(
            InvoicesEntity(iId = 1, iCustomer = "Manolo")
        )
    }
}