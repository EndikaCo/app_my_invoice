package com.endcodev.myinvoice.ui.compose.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.endcodev.myinvoice.data.database.entities.toDomain
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.usecases.GetCustomersUseCase
import com.endcodev.myinvoice.ui.compose.components.MySearchBar
import com.endcodev.myinvoice.ui.compose.screens.home.content.CustomerItem
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.DialogViewModel

@Composable
fun ChooseCustomerDialogActions(
    onDismissRequest: () -> Unit,
    onAcceptRequest: (Customer) -> Unit,
) {
    val viewModel: DialogViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    ChooseCustomerDialog(
        uiState.searchText,
        viewModel::setSearchText,
        uiState.customersList,
        onAcceptRequest,
        onDismissRequest
    )
}

@Composable
fun ChooseCustomerDialog(
    searchText: String,
    onTextChange: (String) -> Unit,
    itemList: List<Customer>,
    onAcceptRequest: (Customer) -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    Column {

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Column {
                MySearchBar(searchText, onTextChange, onFilterClick = {})
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(itemList) { customer ->
                        CustomerItem(customer, onItemClick = {
                            onAcceptRequest(customer)
                        })
                    }
                }
                Box(modifier = Modifier.clickable { onDismissRequest() }.align(Alignment.CenterHorizontally)) {
                    Icon(Icons.Default.Cancel, contentDescription = "", Modifier.size(30.dp)) // todo content desc
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewChooseCustomerDialog() {
    MyInvoiceTheme {

        val customers = GetCustomersUseCase.exampleCustomers().map { it.toDomain() }

        ChooseCustomerDialog(
            "Search",
            {},
            customers,
            {}
        )
    }
}