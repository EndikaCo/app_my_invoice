package com.endcodev.myinvoice.ui.screens.customers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.InvoiceModel
import com.endcodev.myinvoice.ui.screens.invoice.InvoiceItem
import com.endcodev.myinvoice.ui.screens.invoice.ProgressBar
import com.endcodev.myinvoice.ui.screens.invoice.SearchBar
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.CustomersViewModel

@Composable
fun CustomersContent(
) {
    val viewModel: CustomersViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val customers by viewModel.customers.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        SearchBar(searchText, valueChanged = viewModel::onSearchTextChange)
        Spacer(modifier = Modifier.size(16.dp))

        if (isSearching)
            ProgressBar()
        else
            CustomersList(
                Modifier
                    .fillMaxWidth()
                    .weight(1f), customers
            )
    }
}

@Composable
fun CustomersList(modifier: Modifier, customers: List<CustomerModel>) {
    LazyColumn(
        modifier = modifier
    ) {
        items(customers) { customer ->
            CustomerItem(customer)
        }
    }
}

@Composable
fun CustomerImage() {
    Box(
        modifier = Modifier
            .size(50.dp) // Size of the Box (background)
            .background(Color.Gray, CircleShape) // Round background
        , contentAlignment = Alignment.Center // Center content in the Box
    ) {
        Image(
            painter = painterResource(id = R.drawable.person_24),
            contentDescription = "logo",
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
        )
    }
}

@Composable
fun CustomerItem(customer: CustomerModel) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
        ) {
            CustomerImage()
            CustomerPreviewData(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), customer
            )
        }
    }
}

@Composable
fun CustomerPreviewData(modifier: Modifier, customer: CustomerModel) {
    Column(
        modifier = modifier

    ) {
        Text(
            text = customer.cFiscalName,
            modifier = Modifier
                .height(25.dp)
        )
        Text(
            text = customer.cIdentifier,
            modifier = Modifier
                .height(25.dp)
        )
    }
}


@Preview
@Composable
fun MPreview() {
    MyInvoiceTheme {
        CustomersContent()
    }
}

@Preview
@Composable
fun MPreview2() {
    MyInvoiceTheme {
        CustomerItem(CustomerModel(1,  "21321312A", "Example name", "688873827"))
    }
}