package com.endcodev.myinvoice.presentation.compose.screens.home.content

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.endcodev.myinvoice.data.database.entities.toDomain
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.common.FilterModel
import com.endcodev.myinvoice.domain.models.common.FilterType
import com.endcodev.myinvoice.domain.usecases.GetCustomersUseCase
import com.endcodev.myinvoice.presentation.compose.components.MySearchBar
import com.endcodev.myinvoice.presentation.compose.components.FiltersView
import com.endcodev.myinvoice.presentation.compose.components.MyFloatingButton
import com.endcodev.myinvoice.presentation.navigation.DetailsScreen
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme
import com.endcodev.myinvoice.presentation.compose.components.uriToPainterImage
import com.endcodev.myinvoice.presentation.compose.dialogs.FiltersDialog
import com.endcodev.myinvoice.presentation.viewmodels.CustomersViewModel

/**
 * Handles the actions for the [HomeCustomersContent].
 *
 * @param navController The NavController used for navigation actions.
 * @param paddingValues The PaddingValues used for padding in the [HomeCustomersContent].
 */
@Composable
fun HomeCustomersContentActions(navController: NavHostController, paddingValues: PaddingValues) {

    val viewModel: CustomersViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    HomeCustomersContent(
        paddingValues = paddingValues,
        searchText = uiState.searchText,
        customers = uiState.customersList,
        isLoading = uiState.isLoading,
        onSearchTextChange = viewModel::setSearchText,
        onFloatingButtonClick = { navController.navigate(DetailsScreen.Customer.route) },
        onListItemClick = { navController.navigate("${DetailsScreen.Customer.route}/${it}") },
        onFilterClick = { showDialog = true },
        filters = uiState.filters,
        onFiltersChanged = { viewModel.changeFilters(it) },
        onDialogExit = { showDialog = false },
        showDialog = showDialog
    )
}

@Composable
fun HomeCustomersContent(
    onFloatingButtonClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    searchText: String,
    customers: List<Customer>,
    isLoading: Boolean,
    onSearchTextChange: (String) -> Unit,
    onFiltersChanged: (List<FilterModel>) -> Unit,
    filters: List<FilterModel>,
    onDialogExit: () -> Unit,
    showDialog: Boolean,
    onFilterClick: () -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(10.dp)
    )
    {
        MySearchBar(searchText, onSearchTextChange, onFilterClick)
        Spacer(Modifier.size(11.dp))
        FiltersView(onFiltersChanged, filters) //todo not implemented yet
        Spacer(Modifier.size(11.dp))

        if (isLoading)
            ProgressBar()
        else
            CustomersList(Modifier.weight(1f), customers, onListItemClick)
        if (showDialog)
            FiltersDialog(onFiltersChanged, filters, onDialogExit)

        MyFloatingButton(
            modifier = Modifier.align(Alignment.End),
            painter = painterResource(R.drawable.customer_add_24),
            onClick = onFloatingButtonClick
        )
    }
}

@Composable
fun CustomersList(
    modifier: Modifier,
    customers: List<Customer>,
    onItemClick: (String) -> Unit
) {

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
fun CustomerItem(customer: Customer, onItemClick: () -> Unit) {

    var colorFilter: ColorFilter? = null
    var image = uriToPainterImage(customer.cImage)
    if (image == null) {
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
fun CustomerNameAndIdentifier(modifier: Modifier, customer: Customer) {
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
fun HomeCustomersContentPreview() {

    val searchText = "Test"
    val customers = GetCustomersUseCase.exampleCustomers().map { it.toDomain() }
    val isSearching = false
    val onSearchTextChange: (String) -> Unit = {}

    MyInvoiceTheme {
        HomeCustomersContent(
            onFloatingButtonClick = {},
            onListItemClick = {},
            searchText = searchText,
            customers = customers,
            isLoading = isSearching,
            onSearchTextChange = onSearchTextChange,
            onFiltersChanged = {},
            filters = mutableListOf(FilterModel(FilterType.COUNTRY, "Burkina Faso")),
            onDialogExit = {},
            showDialog = false,
            onFilterClick = {},
            paddingValues = PaddingValues()
        )
    }
}