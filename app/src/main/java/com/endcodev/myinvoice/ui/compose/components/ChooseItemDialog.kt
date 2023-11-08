package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.ui.compose.screens.home.customers.CustomerItem
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun ChooseCustomerDialog(
    onDismissRequest: () -> Unit,
    onAcceptRequest: (CustomerModel) -> Unit,
    customers: List<CustomerModel>
) {
    Dialog(onDismissRequest = { }) {
        Column {
            LazyColumn(
                modifier = Modifier
            ) {
                items(customers) { customer ->
                    CustomerItem(customer, onItemClick = { onAcceptRequest(customer) })
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
        ChooseCustomerDialog(
            onDismissRequest = {},
            onAcceptRequest = {},
            listOf(
                CustomerModel(null, "A525251dss", "Jose Miguel", "678595232", ""),
                CustomerModel(null, "225525535F", "Manolo", "763276326", ""),
                CustomerModel(null, "325252525D", "Aurelio Martinez", "42424242112", ""),
            )
        )
    }
}