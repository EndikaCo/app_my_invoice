package com.endcodev.myinvoice.ui.compose.screens.home.invoice

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.domain.models.CustomerModel
import com.endcodev.myinvoice.domain.models.InvoicesModel
import com.endcodev.myinvoice.domain.models.timeNow
import com.endcodev.myinvoice.ui.compose.components.CommonSearchBar
import com.endcodev.myinvoice.ui.compose.screens.home.FloatingActionButton
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
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
        CommonSearchBar(searchText, onTextChanged = onSearchTextChange, onFilterClick = {})
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading)
            ProgressBar()
        else
            InvoicesList(Modifier.weight(1f), invoices, onListItemClick)

        FloatingActionButton(
            Modifier
                .weight(0.08f)
                .align(Alignment.End),
            painter = painterResource(id = R.drawable.invoice_add_24),
            onFloatingButtonClick
        )
        Spacer(modifier = Modifier
            .size(80.dp)
            .fillMaxWidth()) //navbar height
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
fun InvoicesList(
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
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
        ) {
            InvoiceIdAndFiscal(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f), invoice
            )
        }
    }
}

@Composable
fun InvoiceIdAndFiscal(modifier: Modifier, invoice: InvoicesModel) {
    Column(
        modifier = modifier
    ) {
        Row (modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "00${invoice.iId}",
                modifier = Modifier
                    .height(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "15/01/2023",
                modifier = Modifier
                    .height(25.dp)
                    .weight(1f)
            )
            Text(
                text = "Ref ",
                modifier = Modifier
                    .height(25.dp),
                color = Color.Gray
            )
            Text(
                text = "213214",
                modifier = Modifier
                    .height(25.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = invoice.iCustomer.cFiscalName ?: "No customer",
                modifier = Modifier
                    .height(25.dp)
                    .weight(1f), // Take up available space
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "125.57€",
                modifier = Modifier
                    .height(25.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InvoicesContentPreview() {
    val customer = CustomerModel(null, "B9746473", "Manolo S.L")
    MyInvoiceTheme {
        InvoicesContent(searchText = "searchText",
            invoices = listOf(InvoicesModel(iId = 1, iCustomer = customer, iTotal = 125f, iDate = timeNow() )),
            isLoading = false,
            onSearchTextChange = {},
            onFloatingButtonClick = { },
            onListItemClick = { }
        )
    }
}