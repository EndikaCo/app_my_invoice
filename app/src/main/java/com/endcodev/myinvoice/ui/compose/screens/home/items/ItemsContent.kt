package com.endcodev.myinvoice.ui.compose.screens.home.items

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.database.toDomain
import com.endcodev.myinvoice.data.model.FilterModel
import com.endcodev.myinvoice.data.model.ItemModel
import com.endcodev.myinvoice.domain.GetItemsUseCase
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.components.FiltersView
import com.endcodev.myinvoice.ui.compose.screens.home.FloatingActionButton
import com.endcodev.myinvoice.ui.compose.screens.home.customers.customerlist.CustomerImage
import com.endcodev.myinvoice.ui.compose.screens.home.customers.customerlist.FiltersDialog
import com.endcodev.myinvoice.ui.compose.screens.home.invoice.ProgressBar
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.utils.uriToPainterImage
import com.endcodev.myinvoice.ui.viewmodels.ItemsViewModel

@Composable
fun ItemsListContentActions(
    navController: NavHostController,
) {
    val viewModel: ItemsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.userInput.collectAsState()

    var showDialog by remember {
        mutableStateOf(false)
    }

    ItemsListContent(
        searchText = searchText,
        items = uiState.itemsList,
        isLoading = uiState.isLoading,
        onSearchTextChange = viewModel::setSearchText,
        onFloatingButtonClick = { navController.navigate(DetailsScreen.Item.route) },
        onListItemClick = {
            Log.v("TAG", "ItemsListContentActions: $it")
            navController.navigate("${DetailsScreen.Item.route}/${it}")
        },
        onFilterClick = { showDialog = true },
        filters = uiState.filters,
        onFiltersChanged = { viewModel.changeFilters(it) },
        onDialogExit = { showDialog = false },
        showDialog = showDialog
    )
}

@Composable
fun ItemsListContent(
    onFloatingButtonClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    searchText: String,
    items: List<ItemModel>,
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
            ItemsList(
                Modifier.weight(1f),
                items,
                onListItemClick
            )
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
fun ItemsList(
    modifier: Modifier,
    products: List<ItemModel>,
    onItemClick: (String) -> Unit
) {
    LazyVerticalGrid(modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        content = {
            items(products) { item ->
                ProductItem(
                    item = item,
                    onItemClick = { onItemClick(item.iCode) }
                )
            }
        }
    )

}

@Composable
fun ProductItem(item: ItemModel, onItemClick: () -> Unit) {

    var colorFilter: ColorFilter? = null
    var image = uriToPainterImage(item.iImage)
    if (image == null) {
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        image = painterResource(id = R.drawable.filter_24)
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
            ItemNameAndIdentifier(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), item
            )
        }
    }
}

@Composable
fun ItemNameAndIdentifier(modifier: Modifier, item: ItemModel) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = item.iCode,
            modifier = Modifier
                .height(25.dp)
        )
        Text(
            text = item.iName,
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
    val items = GetItemsUseCase.exampleCustomers().map { it.toDomain() }

    MyInvoiceTheme {
        ItemsListContent(
            searchText = searchText,
            items = items,
            isLoading = false,
            onSearchTextChange = {},
            onFloatingButtonClick = { },
            onListItemClick = {},
            onFilterClick = {},
            filters = emptyList(),
            onFiltersChanged = { },
            onDialogExit = { },
            showDialog = false
        )
    }
}


