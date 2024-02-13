package com.endcodev.myinvoice.presentation.compose.screens.home.content

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import com.endcodev.myinvoice.domain.models.common.FilterModel
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.usecases.GetItemsUseCase
import com.endcodev.myinvoice.presentation.compose.components.MySearchBar
import com.endcodev.myinvoice.presentation.compose.components.FiltersView
import com.endcodev.myinvoice.presentation.compose.components.MyFloatingButton
import com.endcodev.myinvoice.presentation.compose.components.filteredImage
import com.endcodev.myinvoice.presentation.compose.dialogs.FiltersDialog
import com.endcodev.myinvoice.presentation.navigation.DetailsScreen
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme
import com.endcodev.myinvoice.presentation.viewmodels.ItemsViewModel

/**
 * Handles the actions for the [HomeProductsContent].
 *
 * @param navController The NavController used for navigation actions.
 * @param paddingValues The PaddingValues used for padding in the [HomeProductsContent].
 */
@Composable
fun HomeProductContentActions(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val viewModel: ItemsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.userInput.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    HomeProductsContent(
        paddingValues = paddingValues,
        searchText = searchText,
        items = uiState.itemsList,
        filters = uiState.filters,
        isLoading = uiState.isLoading,
        showDialog = showDialog,
        onSearchTextChange = viewModel::setSearchText,
        onFloatingButtonClick = { navController.navigate(DetailsScreen.Item.route) },
        onListItemClick = { navController.navigate("${DetailsScreen.Item.route}/${it}") },
        onFilterClick = { showDialog = true },
        onFiltersChanged = { viewModel.changeFilters(it) },
        onDialogExit = { showDialog = false },
    )
}

@Composable
fun HomeProductsContent(
    paddingValues: PaddingValues,
    searchText: String,
    items: List<Product>,
    filters: List<FilterModel>,
    isLoading: Boolean,
    showDialog: Boolean,
    onFloatingButtonClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onFiltersChanged: (List<FilterModel>) -> Unit,
    onDialogExit: () -> Unit,
    onFilterClick: () -> Unit,
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
        FiltersView(onFiltersChanged, filters)
        Spacer(Modifier.size(11.dp))

        if (isLoading)
            ProgressBar()
        else
            ProductList(
                Modifier.weight(1f),
                items,
                onProductClick = { onListItemClick(it.id) }
            )
        if (showDialog)
            FiltersDialog(onFiltersChanged, filters, onDialogExit)

        MyFloatingButton(
            modifier = Modifier.align(Alignment.End),
            painter = painterResource(R.drawable.library_add_24),
            onClick = onFloatingButtonClick
        )
    }
}

@Composable
fun ProductList(
    modifier: Modifier,
    productList: List<Product>,
    onProductClick: (Product) -> Unit
) {
    LazyVerticalGrid(modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 1.dp, vertical = 1.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        content = {
            items(productList) { item ->
                ProductItem(
                    product = item,
                    onProductClick = { onProductClick(item) }
                )
            }
        }
    )

}

@Composable
fun ProductItem(product: Product, onProductClick: () -> Unit) {

    val image = filteredImage(product.image, painterResource(id = R.drawable.no_photo_24))

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
        ) {
            ItemImage(
                image = image.image,
                colorFilter = image.filter
            )
            ItemNameAndIdentifier(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), product
            )
        }
        Text(
            text = product.name,
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
fun ItemNameAndIdentifier(modifier: Modifier, item: Product) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = item.id,
            modifier = Modifier
                .height(25.dp),
            maxLines = 1
        )
        Text(
            text = item.type,
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

    val items = GetItemsUseCase.exampleProducts().map { it.toDomain() }

    MyInvoiceTheme {
        HomeProductsContent(
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
            paddingValues = PaddingValues()
        )
    }
}


