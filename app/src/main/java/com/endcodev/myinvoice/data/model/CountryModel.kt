package com.endcodev.myinvoice.data.model

data class CountryModel(
    val prefix: Int,
    val name: String
)

val allCountriesList: List<CountryModel> = listOf(
    CountryModel(0, "All countries"),
    CountryModel(1, "United States"),
    CountryModel(33, "France"),
    CountryModel(34, "Spain"),
    CountryModel(86, "China"),
)
