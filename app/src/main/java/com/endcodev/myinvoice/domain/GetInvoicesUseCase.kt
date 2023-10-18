package com.endcodev.myinvoice.domain

import com.endcodev.myinvoice.data.repository.InvoicesRepository
import javax.inject.Inject

class GetInvoicesUseCase @Inject constructor(
    private val invoicesRepository : InvoicesRepository
){

    companion object {
        const val TAG = "GetInvoicesUseCase"
    }
}