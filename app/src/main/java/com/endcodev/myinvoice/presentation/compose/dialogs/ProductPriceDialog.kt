package com.endcodev.myinvoice.presentation.compose.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.database.entities.toDomain
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.usecases.GetItemsUseCase
import com.endcodev.myinvoice.presentation.compose.components.uriToPainterImage
import com.endcodev.myinvoice.presentation.compose.screens.details.ItemCost
import com.endcodev.myinvoice.presentation.compose.screens.home.content.ItemImage
import com.endcodev.myinvoice.presentation.compose.screens.home.content.ItemNameAndIdentifier
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme

@Composable
fun ProductDialog(
    sale: SaleItem,
    onDialogAccept: (SaleItem) -> Unit,
    onDialogCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDialogCancel() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        var price by remember { mutableFloatStateOf(sale.price) }
        var quantity by remember { mutableFloatStateOf(sale.quantity) }

        var colorFilter: ColorFilter? = null
        var image = uriToPainterImage(sale.product.image)
        if (image == null) {
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
            image = painterResource(id = R.drawable.no_photo_24)
        }
        Column(
            Modifier
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onBackground,
                    MaterialTheme.shapes.medium
                )
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                    .background(Color.Gray)
            ) {
                ItemImage(image = image, colorFilter = colorFilter)
                ItemNameAndIdentifier(
                    Modifier
                        .padding(start = 12.dp)
                        .weight(1f), sale.product
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
            ) {

                ItemCost(
                    label = "Quantity",
                    amount = quantity,
                    onAmountChanged = { newQuantity ->
                        quantity = newQuantity
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                ItemCost(
                    label = "price",
                    amount = price,
                    onAmountChanged = { newPrice -> price = newPrice }
                )
                Spacer(modifier = Modifier.width(4.dp))
                ItemCost(
                    label = "Total",
                    amount = sale.quantity * sale.price,
                    onAmountChanged = {}
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Button(onClick = { onDialogCancel() }) {
                    Text(text = "Cancel")
                }
                Button(onClick = {
                    onDialogAccept(
                        sale.copy(
                            price = price,
                            quantity = quantity
                        )
                    )
                }) {
                    Text(text = "Accept")
                }
            }
        }
    }
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SaleItemDialogDialogPreview() {
    MyInvoiceTheme {

        val products = GetItemsUseCase.exampleProducts().map { it.toDomain() }

        ProductDialog(
            SaleItem(id = 1, products[1], 5F, 10F, 13),
            {},
            {},
        )
    }
}


