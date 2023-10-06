package com.endcodev.myinvoice.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.endcodev.myinvoice.data.model.InvoiceModel
import com.endcodev.myinvoice.ui.viewmodels.InvoicesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoicesContent() {
    val viewModel = viewModel<InvoicesViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val invoices by viewModel.invoices.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(searchText, viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        if(isSearching) {
            ProgressBar()
        } else {
            InvoiceList(Modifier.fillMaxWidth().weight(1f), invoices)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, viewModel: InvoicesViewModel) {
    TextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") }
    )
}

@Composable
fun ProgressBar(){
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun InvoiceList(modifier: Modifier, invoices: List<InvoiceModel>){
    LazyColumn(
        modifier = modifier
    ) {
        items(invoices) { invoices ->
            InvoiceItem(invoices)
        }
    }
}

@Composable
fun InvoiceItem(invoice: InvoiceModel) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 8.dp) //between items
            .fillMaxWidth()
            .clickable { }
    ){


        Row(modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)) {
            InvoicePreviewData(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), invoice)
        }
    }
}

@Composable
fun InvoicePreviewData(modifier: Modifier, invoice: InvoiceModel) {
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