package com.endcodev.myinvoice.domain.models.invoice

import com.endcodev.myinvoice.domain.models.customer.Customer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class InvoiceUiState(

    val invoice: Invoice = Invoice(
        id = 0,
        date = getDate(),
        customer = Customer(
            image = null,
            fiscalName = "Select Customer",
            id = "0"
        )
    ),
    val isLoading : Boolean = false,
    val isSaveEnabled : Boolean = false,
    val isDeleteEnabled: Boolean = false
)

fun getDate(): String {
    val date: LocalDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return date.format(formatter).toString()
}