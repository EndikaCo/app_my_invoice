package com.endcodev.myinvoice.ui.compose.screens.home.invoice

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.InvoiceUiState
import com.endcodev.myinvoice.ui.compose.components.AcceptCancelButtons
import com.endcodev.myinvoice.ui.compose.components.CDatePicker
import com.endcodev.myinvoice.ui.compose.components.ChooseCustomerDialog
import com.endcodev.myinvoice.ui.compose.components.DocSelection
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.InvoiceInfoViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
fun InvoiceDetailActions(
    invoiceId: String?,
    navController: NavHostController
) {
    val viewModel: InvoiceInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    InvoiceInfoScreen(onNavButtonClick = {}, uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceInfoScreen(
    onNavButtonClick: () -> Unit,
    uiState: InvoiceUiState
) {

    val state = rememberDatePickerState()

    val openDialog = remember { mutableStateOf(false) }

    if (uiState.showDialog)
        ChooseCustomerDialog(
            onDismissRequest = {},
            onAcceptRequest = {},
            customers = uiState.customersList!!//todo null
        )
    else
        Scaffold(
            topBar = { },
            content = { innerPadding ->
                InvoiceInfoContent(
                    innerPadding = innerPadding,
                    onDateClick = { openDialog.value = true })
            },
            bottomBar = {
                AcceptCancelButtons(enabled = true, onAcceptClick = { openDialog.value = true }) {}
            }
        )

    if (openDialog.value)
        CDatePicker(openDialog = { openDialog.value = it }, state, newDate = {Log.v("new date", it.selectedDateMillis.toString())})
}

fun now(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}

@Composable
fun InvoiceInfoContent(innerPadding: PaddingValues, onDateClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            InvoiceNum(invoiceId = "001")
            Spacer(modifier = Modifier.width(8.dp))
            InvoiceDate("", onClick = { onDateClick() }) //todo
            Spacer(modifier = Modifier.width(8.dp))
            DocSelection(onSelection = { }, docs = listOf("Invoice", "Receipt"))
        }
        SelectCustomer(onClick = { }) // todo open customers dialog
    }
}

@Composable
fun InvoiceNum(invoiceId: String) {
    OutlinedTextField(
        value = invoiceId,
        onValueChange = { },
        label = { Text(text = "invoice") },
        modifier = Modifier.width(80.dp)
    )
}

@Composable
fun InvoiceDate(date1: String, onClick: () -> Unit, dateChanged : (String) -> Unit = {}) {
    var date = date1
    if (date1.isEmpty() || date1.isBlank())
        date = now()

    OutlinedTextField(
        leadingIcon = {
            Image(
                //show calendar icon
                painterResource(id = R.drawable.calendar_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.clickable { onClick() }
            )
        },
        value = date,
        onValueChange = {dateChanged(it)},
        label = { Text(text = "date") },
        modifier = Modifier.width(160.dp)

    )
}

@Composable
fun SelectCustomer(
    content: String = "Select customer",
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(20)
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(start = 16.dp, end = 16.dp)
            .border(shape = shape, width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        Image(
            painter = painterResource(id = R.drawable.image_search_24),
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.clickable { onClick() }
        )
        Text(
            text = content,
            fontWeight = FontWeight.W300,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
        )
    }
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewInvoiceInfoScreen() {
    MyInvoiceTheme {
        InvoiceInfoScreen(onNavButtonClick = {}, uiState = InvoiceUiState())
    }
}
