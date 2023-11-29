package com.endcodev.myinvoice.ui.compose.screens.home.content

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.endcodev.myinvoice.domain.models.FilterModel
import com.endcodev.myinvoice.domain.models.ItemModel
import com.endcodev.myinvoice.domain.usecases.GetItemsUseCase
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.components.FiltersView
import com.endcodev.myinvoice.ui.compose.components.FloatingActionButton
import com.endcodev.myinvoice.ui.compose.dialogs.FiltersDialog
import com.endcodev.myinvoice.ui.compose.components.uriToPainterImage
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.ItemsViewModel

@Composable
fun ItemsListContentActions(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val viewModel: ItemsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.userInput.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    ItemsListContent(
        paddingValues = paddingValues,
        searchText = searchText,
        items = uiState.itemsList,
        isLoading = uiState.isLoading,
        onSearchTextChange = viewModel::setSearchText,
        onFloatingButtonClick = { navController.navigate(DetailsScreen.Item.route) },
        onListItemClick = { navController.navigate("${DetailsScreen.Item.route}/${it}") },
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
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues).padding(10.dp)
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
            modifier = Modifier
                .weight(0.08f)
                .align(Alignment.End),
            painter = painterResource(R.drawable.customer_add_24),
            onAddButtonClick = onFloatingButtonClick
        )
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
        contentPadding = PaddingValues(horizontal = 1.dp, vertical = 1.dp),
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
        image = painterResource(id = R.drawable.no_photo_24)
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
        ) {
            ItemImage(
                image = image,
                colorFilter = colorFilter
            )
            ItemNameAndIdentifier(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), item
            )
        }
        Text(
            text = item.iName,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
fun ItemImage(image: Painter, colorFilter: ColorFilter?) {

    Box(
        modifier = Modifier
            .size(50.dp) // Size of the Box (background)
            , contentAlignment = Alignment.Center // Center content in the Box
    ) {
        Image(
            painter = image,
            contentDescription = "customer Image",
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter
        )
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
                .height(25.dp),
            maxLines = 1
        )
        Text(
            text = item.iType,
            modifier = Modifier
                .height(25.dp),
            maxLines = 1

        )
    }
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemContentPreview() {

    val items = GetItemsUseCase.exampleCustomers().map { it.toDomain() }

    MyInvoiceTheme {
        ItemsListContent(
            onFloatingButtonClick = { },
            onListItemClick = {},
            searchText = "Search",
            items = items,
            isLoading = false,
            onSearchTextChange = {},
            onFiltersChanged = { },
            filters = emptyList(),
            onDialogExit = { },
            showDialog = false,
            onFilterClick = {},
            paddingValues = PaddingValues( )
        )
    }
}


