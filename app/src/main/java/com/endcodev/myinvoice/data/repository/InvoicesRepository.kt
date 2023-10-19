package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.database.InvoicesDao
import com.endcodev.myinvoice.data.database.InvoicesEntity
import com.endcodev.myinvoice.data.database.toDomain
import com.endcodev.myinvoice.data.model.InvoicesModel
import javax.inject.Inject

class InvoicesRepository @Inject constructor(
    private val invoicesDao: InvoicesDao
) {

    suspend fun getAllInvoicesFromDB(): List<InvoicesModel> {
        val itemsList: List<InvoicesEntity> = invoicesDao.getAllInvoices()
        return itemsList.map { it.toDomain() }
    }


    suspend fun insertAllItems(invoiceList: MutableList<InvoicesEntity>) {
        invoicesDao.insertAllInvoices(invoiceList)
    }

    suspend fun insertItem(invoice: InvoicesEntity) {
        invoicesDao.insertInvoice(invoice)
    }
}