package com.endcodev.myinvoice.data.model

data class FilterModel(
    val type : FilterType,
    val text : String
)

enum class FilterType { NEW, COUNTRY, ACTIVITY}

