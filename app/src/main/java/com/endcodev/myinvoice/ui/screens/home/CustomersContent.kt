package com.endcodev.myinvoice.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.CustomersViewModel

@Composable
fun CustomersScreen(
    onClick: () -> Unit,
    viewModel: CustomersViewModel = hiltViewModel()
) {
    val customersList by viewModel.customersList.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)

    Column {
        SearchBar(onClick)
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(Modifier.fillMaxWidth()) {
            if(customersList != null)
                items(customersList!!) { customer ->
                CustomerItem(customer = customer)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onClick: () -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = { IconButton(onClick = { onClick() }) { Icon(Icons.Rounded.AddCircle, "Clear Icon") } },
        modifier = Modifier.fillMaxWidth()
    )
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
    ){
        Row(modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)) {
            CustomerImage()
            CustomerPreviewData(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), customer)
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
        CustomersScreen(onClick = {})
    }
}

@Preview
@Composable
fun MPreview2() {
    MyInvoiceTheme {
        CustomerItem(CustomerModel(1, null, "21321312A", "Example name", "688873827"))
    }
}