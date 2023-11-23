package com.endcodev.myinvoice.domain.models

data class FilterModel(
    val type : FilterType,
    val text : String
)

enum class FilterType { COUNTRY, ACTIVITY}

