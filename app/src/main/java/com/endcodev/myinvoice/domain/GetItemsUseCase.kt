package com.endcodev.myinvoice.domain

import com.endcodev.myinvoice.data.model.ItemsModel
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
) {

    operator fun invoke(): List<ItemsModel> {
        return exampleCustomers()
    }

    private fun exampleCustomers(): MutableList<ItemsModel> {
        return arrayListOf(
            ItemsModel(iCode = "1", iDescription = "example1", iName = "example1", iImage = null),
            ItemsModel(iCode = "2", iDescription = "example2", iName = "example3", iImage = null),
            ItemsModel(iCode = "3", iDescription = "example2", iName = "example3", iImage = null),
        )
    }
}