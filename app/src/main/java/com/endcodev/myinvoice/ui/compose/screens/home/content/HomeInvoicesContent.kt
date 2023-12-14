package com.endcodev.myinvoice.ui.compose.screens.home.content

import android.content.res.Configuration
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
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.domain.models.invoice.getDate
import com.endcodev.myinvoice.ui.compose.components.MySearchBar
import com.endcodev.myinvoice.ui.compose.components.MyFloatingButton
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.InvoicesViewModel

/**
 * Handles the actions for the [HomeInvoicesContent].
 *
 * @param navController The NavController used for navigation actions.
 * @param paddingValues The PaddingValues used for padding in the [HomeInvoicesContent].
 */
@Composable
fun HomeInvoicesContentActions(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val viewModel: InvoicesViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val invoices by viewModel.invoices.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    HomeInvoicesContent(
        paddingValues = paddingValues,
        searchText = searchText,
        invoices = invoices,
        isLoading = isSearching,
        onSearchTextChange = viewModel::onSearchTextChange,
        onFloatingButtonClick = { navController.navigate(DetailsScreen.Invoice.route) },
        onListItemClick = { navController.navigate("${DetailsScreen.Invoice.route}/${it}") },
    )
}

@Composable
fun HomeInvoicesContent(
    onFloatingButtonClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    searchText: String,
    invoices: List<Invoice>,
    isLoading: Boolean,
    onSearchTextChange: (String) -> Unit,
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(12.dp)
    ) {
        MySearchBar(
            searchText = searchText,
            onTextChanged = onSearchTextChange,
            onFilterClick = {/*Todo*/ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading)
            ProgressBar()
        else
            InvoicesList(Modifier.weight(1f), invoices, onListItemClick)

        MyFloatingButton(
            modifier = Modifier.align(Alignment.End),
            painter = painterResource(id = R.drawable.invoice_add_24),
            onClick = onFloatingButtonClick
        )
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
    invoices: List<Invoice>,
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
fun InvoiceItem(invoice: Invoice, onItemClick: () -> Unit) {
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
fun InvoiceIdAndFiscal(modifier: Modifier, invoice: Invoice) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "00${invoice.iId}",
                modifier = Modifier
                    .height(25.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = invoice.iDate,
                modifier = Modifier
                    .height(25.dp)
                    .weight(1f)
            )
            Text(
                text = "ref:",
                modifier = Modifier
                    .height(25.dp),
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = invoice.iReference,
                modifier = Modifier
                    .height(25.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = invoice.iCustomer.cFiscalName,
                modifier = Modifier
                    .height(25.dp)
                    .weight(1f), // Take up available space
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "${invoice.iTotal}€",
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

    val customer = Customer(null, "B9746473", "Manolo S.L")

    MyInvoiceTheme {
        HomeInvoicesContent(
            onFloatingButtonClick = { },
            onListItemClick = { },
            searchText = "searchText",
            invoices = listOf(
                Invoice(
                    iId = 1,
                    iCustomer = customer,
                    iTotal = 125.54f,
                    iDate = getDate(),
                    iReference = "HK4325"
                )
            ),
            isLoading = false,
            onSearchTextChange = {},
            paddingValues = PaddingValues(0.dp)
        )
    }
}