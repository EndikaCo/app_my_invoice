package com.endcodev.myinvoice.data

data class CustomerModel(
    val cId : Int,
    val cImage: Int?,
    val cIdentifier : String,
    val cFiscalName : String,
    val cTelephone : String
)