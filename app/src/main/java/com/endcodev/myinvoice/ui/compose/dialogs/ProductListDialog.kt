package com.endcodev.myinvoice.ui.compose.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.endcodev.myinvoice.data.database.entities.toDomain
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.usecases.GetItemsUseCase
import com.endcodev.myinvoice.ui.compose.components.MySearchBar
import com.endcodev.myinvoice.ui.compose.screens.home.content.ProductList
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.AddProductViewModel

@Composable
fun InvoiceProductAddDialogActions(
    onDialogAccept: (Product) -> Unit,
    onDialogCancel: () -> Unit,
) {
    val viewModel: AddProductViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    InvoiceProductAddDialog(
        uiState.searchText,
        viewModel::setSearchText,
        uiState.itemsList,
        onDialogAccept,
        onDialogCancel
    )
}

@Composable
fun InvoiceProductAddDialog(
    searchText: String,
    onTextChange: (String) -> Unit,
    itemList: List<Product>,
    onDialogAccept: (Product) -> Unit,
    onDialogCancel: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDialogCancel() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column {
            MySearchBar(searchText, onTextChange, onFilterClick = {})
            Spacer(modifier = Modifier.height(16.dp))

            ProductList(
                Modifier,
                itemList,
                onDialogAccept
            )
            Box(modifier = Modifier.clickable { onDialogCancel() }.align(Alignment.CenterHorizontally)) {
                Icon(Icons.Default.Cancel, contentDescription = "", Modifier.size(30.dp)) // todo content desc
            }
        }
    }
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InvoiceProductAddDialogPreview() {
    MyInvoiceTheme {

        val products = GetItemsUseCase.exampleProducts().map { it.toDomain() }

        InvoiceProductAddDialog(
            "Search",
            {},
            products,
            {},
            {}
        )
    }
}


