package com.endcodev.myinvoice.ui.compose.screens.home.customers.customerlist

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.database.toDomain
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.FilterModel
import com.endcodev.myinvoice.data.model.FilterType
import com.endcodev.myinvoice.domain.GetCustomersUseCase
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.components.FiltersView
import com.endcodev.myinvoice.ui.compose.screens.home.FloatingActionButton
import com.endcodev.myinvoice.ui.compose.screens.home.invoice.ProgressBar
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

    var showDialog by remember {
        mutableStateOf(false)
    }

    CustomersListContent(
        searchText = uiState.searchText,
        customers = uiState.customersList,
        isLoading = uiState.isLoading,
        onSearchTextChange = viewModel::setSearchText,
        onFloatingButtonClick = { navController.navigate(DetailsScreen.Customer.route) },
        onListItemClick = { navController.navigate("${DetailsScreen.Customer.route}/${it}") },
        onFilterClick = {showDialog = true  },
        filters = uiState.filters,
        onFiltersChanged = { viewModel.changeFilters(it) },
        onDialogExit = { showDialog = false },
        showDialog = showDialog
    )
}

@Composable
fun CustomersListContent(
    onFloatingButtonClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    searchText: String,
    customers: List<CustomerModel>,
    isLoading: Boolean,
    onSearchTextChange: (String) -> Unit,

    onFiltersChanged: (List<FilterModel>) -> Unit,
    filters: List<FilterModel>,
    onDialogExit: () -> Unit,
    showDialog: Boolean,
    onFilterClick: () -> Unit,
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        CommonSearchBar(searchText, onSearchTextChange, onFilterClick)
        Spacer(Modifier.size(11.dp))
        FiltersView(onFiltersChanged, filters)
        Spacer(Modifier.size(11.dp))

        if (isLoading)
            ProgressBar()
        else
            CustomersList(Modifier.weight(1f), customers, onListItemClick)
        if (showDialog)
            FiltersDialog(onFiltersChanged, filters, onDialogExit)

        FloatingActionButton(
            Modifier
                .weight(0.08f)
                .align(Alignment.End),
            painterResource(R.drawable.customer_add_24),
            onFloatingButtonClick
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
fun CustomerImage(image: Painter, colorFilter: ColorFilter?) {

    Box(
        modifier = Modifier
            .size(50.dp) // Size of the Box (background)
            .border(
                border = BorderStroke(width = 1.dp, color = colorScheme.onBackground),
                shape = CircleShape
            ), contentAlignment = Alignment.Center // Center content in the Box
    ) {
        Image(
            painter = image,
            contentDescription = "customer Image",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter
        )
    }
}

@Composable
fun CustomerItem(customer: CustomerModel, onItemClick: () -> Unit) {

    var colorFilter : ColorFilter? = null
    var image = uriToPainterImage(customer.cImage)
    if (image == null){
        colorFilter = ColorFilter.tint(color = colorScheme.onBackground)
        image = painterResource(id = R.drawable.person_24)
    }

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
                image = image,
                colorFilter = colorFilter
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


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomersContentPreview() {

    val searchText = "Test"
    val customers = GetCustomersUseCase.exampleCustomers().map { it.toDomain() }
    val isSearching = false
    val onSearchTextChange: (String) -> Unit = {}

    MyInvoiceTheme {
        CustomersListContent(
            onFloatingButtonClick = {},
            onListItemClick = {},
            searchText = searchText,
            customers = customers,
            isLoading = isSearching,
            onSearchTextChange = onSearchTextChange,
            onFilterClick = {},
            filters = mutableListOf(FilterModel(FilterType.COUNTRY, "Burkina Faso")),
            onFiltersChanged = {},
            onDialogExit = {},
            showDialog = false
        )
    }
}