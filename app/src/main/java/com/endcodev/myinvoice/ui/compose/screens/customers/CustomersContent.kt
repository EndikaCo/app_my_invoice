package com.endcodev.myinvoice.ui.compose.screens.customers

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.screens.FloatingActionButton
import com.endcodev.myinvoice.ui.compose.screens.invoice.ProgressBar
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.CustomersViewModel
import kotlin.reflect.KFunction1

@Composable
fun CustomersContentActions(
    navController: NavHostController,
){
    val viewModel: CustomersViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val customers by viewModel.customers.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    CustomersContent(
        searchText = searchText,
        customers = customers,
        isSearching = isSearching,
        onSearchTextChange = viewModel::onSearchTextChange,
        onCleanClick = viewModel::clearSearchText,
        onButtonClick = {navController.navigate(DetailsScreen.Customer.route)},
        onItemClick = {navController.navigate("${DetailsScreen.Customer.route}/${it}")}
    )
}

@Composable
fun CustomersContent(
    onButtonClick: () -> Unit,
    onItemClick: (String) -> Unit,
    searchText: String,
    customers: List<CustomerModel>,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit,
    onCleanClick: () -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        CommonSearchBar(searchText, onSearchTextChange, onCleanClick)
        Spacer(Modifier.size(16.dp))

        if (isSearching)
            ProgressBar()
        else
            CustomersList(Modifier.weight(1f), customers, onItemClick)

        FloatingActionButton(
            Modifier
                .weight(0.08f)
                .align(Alignment.End),
            painterResource(R.drawable.customer_add_24),
            onButtonClick
        )
        Spacer(Modifier.size(80.dp))
    }
}

@Composable
fun CustomersList(
    modifier: Modifier,
    customers: List<CustomerModel>,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(customers) { customer ->
            CustomerItem(customer, onItemClick = { onItemClick(customer.cIdentifier) })
        }
    }
}

@Composable
fun CustomerImage(image: Painter) {
    Box(
        modifier = Modifier
            .size(50.dp) // Size of the Box (background)
            .background(Color.Gray, CircleShape) // Round background
        , contentAlignment = Alignment.Center // Center content in the Box
    ) {
        Image(
            painter = image,
            contentDescription = "logo",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun CustomerItem(customer: CustomerModel, onItemClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
        ) {
            CustomerImage(
                uriToPainterImage(
                    uri = customer.cImage,
                    default = painterResource(id = R.drawable.image_search_24)
                )
            )
            CustomerNameAndIdentifier(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), customer
            )
        }
    }
}

@Composable
fun CustomerNameAndIdentifier(modifier: Modifier, customer: CustomerModel) {
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
fun CustomersContentPreview() {

    // Define your test data and actions here
    val searchText = "Test"
    val customers = listOf<CustomerModel>() // replace with your test data
    val isSearching = false

    // Define a test ViewModel or a way to provide test actions
    val onSearchTextChange: (String) -> Unit = {}

    MyInvoiceTheme {
        CustomersContent(
            onButtonClick = {},
            onItemClick = {},
            searchText = searchText,
            customers = customers,
            isSearching = isSearching,
            onSearchTextChange = onSearchTextChange,
            onCleanClick = {}
        )
    }
}

@Composable
fun Filters() {
    val filterList = listOf("Add filter")
    LazyRow {
        items(filterList) { filter ->
            FilterItem(filter)
        }
    }
}

@Composable
fun FilterItem(filter: String) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(50.dp)
    )
}
