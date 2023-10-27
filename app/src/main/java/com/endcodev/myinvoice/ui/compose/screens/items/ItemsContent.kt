package com.endcodev.myinvoice.ui.compose.screens.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.ItemsModel
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.screens.FloatingActionButton
import com.endcodev.myinvoice.ui.compose.screens.customers.CustomerImage
import com.endcodev.myinvoice.ui.compose.screens.invoice.ProgressBar
import com.endcodev.myinvoice.ui.viewmodels.ItemsViewModel

@Composable
fun ItemsContent(onButtonClick: () -> Unit) {
    val viewModel: ItemsViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val items by viewModel.items.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        CommonSearchBar(searchText, valueChanged = viewModel::onSearchTextChange, onCleanClick = {})
        Spacer(modifier = Modifier.size(16.dp))

        if (isSearching)
            ProgressBar()
        else
            ItemsList(Modifier.weight(1f), items)

        FloatingActionButton(
            Modifier
                .weight(0.08f)
                .align(Alignment.End),
            painter = painterResource(id = R.drawable.item_add_24),
            onButtonClick
        )
        Spacer(modifier = Modifier.size(80.dp))
    }
}

@Composable
fun ItemsList(modifier: Modifier, items: List<ItemsModel>) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items) { item ->
            MyItem(item)
        }
    }
}

@Composable
fun MyItem(item: ItemsModel) {
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
            CustomerImage(painterResource(id = R.drawable.image_search_24))
            ItemPreviewData(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), item
            )
        }
    }
}

@Composable
fun ItemPreviewData(modifier: Modifier, item: ItemsModel) {
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


