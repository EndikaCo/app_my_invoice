package com.endcodev.myinvoice.ui.compose.screens.home.invoice

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.InvoicesModel
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.screens.home.FloatingActionButton
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.viewmodels.InvoicesViewModel


@Composable
fun InvoicesListContentActions(
    navController: NavHostController,
) {
    val viewModel: InvoicesViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val invoices by viewModel.invoices.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    InvoicesContent(
        searchText = searchText,
        invoices = invoices,
        isLoading = isSearching,
        onSearchTextChange = viewModel::onSearchTextChange,
        onFloatingButtonClick = { navController.navigate(DetailsScreen.Invoice.route) },
        onListItemClick = { navController.navigate("${DetailsScreen.Invoice.route}/${it}") },
    )
}

@Composable
fun InvoicesContent(
    onFloatingButtonClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    searchText: String,
    invoices: List<InvoicesModel>,
    isLoading: Boolean,
    onSearchTextChange: (String) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CommonSearchBar(searchText, valueChanged = onSearchTextChange, onFilterClick = {})
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading)
            ProgressBar()
        else
            InvoiceList(Modifier.weight(1f), invoices, onListItemClick)

        FloatingActionButton(
            Modifier
                .weight(0.08f)
                .align(Alignment.End),
            painter = painterResource(id = R.drawable.invoice_add_24),
            onFloatingButtonClick
        )
        Spacer(modifier = Modifier.size(80.dp))
    }
}

@Composable
fun ProgressBar() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun InvoiceList(
    modifier: Modifier,
    invoices: List<InvoicesModel>,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(invoices) { invoice ->
            InvoiceItem(invoice, onItemClick = { onItemClick(invoice.iId.toString()) })
        }
    }
}

@Composable
fun InvoiceItem(invoice: InvoicesModel, onItemClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 8.dp) //between items
            .fillMaxWidth()
            .clickable { onItemClick()}
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
        ) {
            InvoicePreviewData(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), invoice
            )
        }
    }
}

@Composable
fun InvoicePreviewData(modifier: Modifier, invoice: InvoicesModel) {
    Column(
        modifier = modifier

    ) {
        Text(
            text = "Invoice ${invoice.iId}",
            modifier = Modifier
                .height(25.dp)
        )
        Text(
            text = invoice.iCustomer,
            modifier = Modifier
                .height(25.dp)
        )
    }
}

@Preview
@Composable
fun InvoicesContentPreview() {
    //InvoicesContent()
}