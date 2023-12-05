package com.endcodev.myinvoice.data.repository

import com.endcodev.myinvoice.data.database.daos.InvoicesDao
import com.endcodev.myinvoice.data.database.entities.InvoicesEntity
import com.endcodev.myinvoice.data.database.entities.toDomain
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import javax.inject.Inject

class InvoicesRepository @Inject constructor(
    private val invoicesDao: InvoicesDao
) {

    suspend fun getAllInvoicesFromDB(): List<Invoice> {
        val itemsList: List<InvoicesEntity> = invoicesDao.getAllInvoices()
        return itemsList.map { it.toDomain()!! }
    }

    suspend fun insertAllItems(invoiceList: MutableList<InvoicesEntity>) {
        invoicesDao.insertAllInvoices(invoiceList)
    }

    fun getInvoiceById(invoiceId: String): Invoice {
        val invoice : InvoicesEntity = invoicesDao.getInvoiceById(invoiceId)
        return invoice.toDomain()!!
    }

    suspend fun insertInvoice(invoice: Invoice) {
        invoicesDao.insertInvoice(invoice.toEntity())
    }

    suspend fun updateInvoice(invoice: Invoice) {
        invoicesDao.updateInvoice(invoice.toEntity())
    }
}