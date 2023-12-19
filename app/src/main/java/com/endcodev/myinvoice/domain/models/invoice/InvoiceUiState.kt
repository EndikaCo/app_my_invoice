package com.endcodev.myinvoice.domain.models.invoice

import com.endcodev.myinvoice.domain.models.customer.Customer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class InvoiceUiState(

    val isLoading : Boolean = false,

    val invoice: Invoice = Invoice(
        iId = 0,
        iDate = getDate(),
        iCustomer = Customer(
            cImage = null,
            cFiscalName = "Select Customer",
            cIdentifier = "0"
        )
    )
)

fun getDate(): String {
    val date: LocalDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return date.format(formatter).toString()
}