package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.database.InvoicesDao
import com.endcodev.myinvoice.data.database.InvoicesEntity
import com.endcodev.myinvoice.data.database.toDomain
import com.endcodev.myinvoice.domain.models.InvoicesModel
import javax.inject.Inject

class InvoicesRepository @Inject constructor(
    private val invoicesDao: InvoicesDao
) {

    suspend fun getAllInvoicesFromDB(): List<InvoicesModel> {
        val itemsList: List<InvoicesEntity> = invoicesDao.getAllInvoices()
        return itemsList.map { it.toDomain()!! }
    }

    suspend fun insertAllItems(invoiceList: MutableList<InvoicesEntity>) {
        invoicesDao.insertAllInvoices(invoiceList)
    }

    fun getInvoiceById(invoiceId: String): InvoicesModel {
        val invoice : InvoicesEntity = invoicesDao.getInvoiceById(invoiceId)
        return invoice.toDomain()!!
    }

    suspend fun insertInvoice(invoice: InvoicesModel) {
        invoicesDao.insertInvoice(invoice.toEntity())
    }

    suspend fun updateInvoice(invoice: InvoicesModel) {
        invoicesDao.updateInvoice(invoice.toEntity())
    }
}