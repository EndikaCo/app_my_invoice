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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.database.toDomain
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.FilterModel
import com.endcodev.myinvoice.data.model.FilterType
import com.endcodev.myinvoice.domain.GetCustomersUseCase
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.screens.FloatingActionButton
import com.endcodev.myinvoice.ui.compose.screens.invoice.ProgressBar
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.utils.uriToPainterImage
import com.endcodev.myinvoice.ui.viewmodels.CustomersViewModel

@Composable
fun CustomersListContentActions(
    navController: NavHostController,
) {
    val viewModel: CustomersViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    CustomersListContent(
        searchText = uiState.searchText,
        customers = uiState.customersList,
        isSearching = uiState.isLoading,
        onSearchTextChange = viewModel::setSearchText,
        onButtonClick = { navController.navigate(DetailsScreen.Customer.route) },
        onItemClick = { navController.navigate("${DetailsScreen.Customer.route}/${it}") },
        onFilterClick = { viewModel.manageFilter(it) },
        showDialog = uiState.showDialog,
        filters = uiState.filters,
        onFiltersChanged = {viewModel.changeFilters(it)},
        onDialogExit = { viewModel.dialogClose() }
    )
}

@Composable
fun CustomersListContent(
    onButtonClick: () -> Unit,
    onItemClick: (String) -> Unit,
    searchText: String,
    customers: List<CustomerModel>,
    isSearching: Boolean,
    onFilterClick: (FilterModel) -> Unit,
    showDialog: Boolean,
    onSearchTextChange: (String) -> Unit,
    onFiltersChanged: (MutableList<FilterModel>) -> Unit,
    filters : MutableList<FilterModel>,
    onDialogExit : () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        CommonSearchBar(searchText, onSearchTextChange)
        Spacer(Modifier.size(11.dp))
        FiltersView(onFilterClick, filters)
        Spacer(Modifier.size(11.dp))

        if (isSearching)
            ProgressBar()
        else
            CustomersList(Modifier.weight(1f), customers, onItemClick)
        if (showDialog)
            FiltersDialog(onFiltersChanged, filters, onDialogExit)

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
                .height(5.dp)
                .width(5.dp)
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
            CustomerImage(uriToPainterImage(
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

@Composable
fun FiltersView(onFilterClick: (FilterModel) -> Unit, filters : List<FilterModel>) {

    LazyRow(Modifier.fillMaxWidth()) {
        items(filters) {
            FilterItem(it, onFilterClick)
        }
    }
}

@Composable
fun FilterItem(filter: FilterModel, onFilterClick: (FilterModel) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable { onFilterClick(filter) }
            .padding(start = 5.dp, end = 5.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.purple_200),
            contentColor = Color.Black
        )
    ) {
        Box(
            Modifier
                .height(25.dp)
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = filter.text,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun CustomersContentPreview() {

    val searchText = "Test"
    val customers = GetCustomersUseCase.exampleCustomers().map { it.toDomain() }
    val isSearching = false
    val onSearchTextChange: (String) -> Unit = {}

    MyInvoiceTheme {
        CustomersListContent(
            onButtonClick = {},
            onItemClick = {},
            searchText = searchText,
            customers = customers,
            isSearching = isSearching,
            onSearchTextChange = onSearchTextChange,
            onFilterClick = {},
            showDialog = false,
            filters = mutableListOf(FilterModel(FilterType.NEW, "new")),
            onFiltersChanged = {},
            onDialogExit = {}
            )
    }
}