package com.endcodev.myinvoice.ui.compose.screens.invoice

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.ItemsModel
import com.endcodev.myinvoice.ui.compose.components.BottomButtons
import com.endcodev.myinvoice.ui.compose.components.ChooseCustomerDialog
import com.endcodev.myinvoice.ui.compose.screens.items.ItemsList
import com.endcodev.myinvoice.ui.viewmodels.InvoiceInfoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceInfoScreen(
    viewModel: InvoiceInfoViewModel = hiltViewModel(),
    onNavButtonClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
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
                InvoiceContent(innerPadding, viewModel)
            },
            bottomBar = {
                BottomButtons(enabled = true, onAcceptClick = { /*TODO*/ }) {}
            }
        )
}

fun now(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceContent(innerPadding: PaddingValues, viewModel: InvoiceInfoViewModel) {
    Column(modifier = Modifier.padding(innerPadding)) {
        Row {
            OutlinedTextField(
                value = "001",
                onValueChange = {},
                label = { Text(text = "invoice") },
                modifier = Modifier
                    .width(110.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
            val date = now()
            OutlinedTextField(
                value = date,
                onValueChange = {},
                label = { Text(text = "date") },
                modifier = Modifier
                    .width(140.dp)
                    .padding(top = 16.dp)
            )
        }
        SelectCustomer(onClick = { viewModel })
        val list = listOf(
            ItemsModel(null, "1", "dsadsa", "fsafasf"),
            ItemsModel(null, "12", "dsadsa", "fsafasf"),
            ItemsModel(null, "12", "dsadsa", "fsafasf"),
            ItemsModel(null, "12", "dsadsa", "fsafasf"),
            ItemsModel(null, "12", "dsadsa", "fsafasf"),
            ItemsModel(null, "12", "dsadsa", "fsafasf"),
            ItemsModel(null, "12", "dsadsa", "fsafasf"),
            ItemsModel(null, "12", "dsadsa", "fsafasf"),
            ItemsModel(null, "7", "dsadsa", "fsafasf"),
            ItemsModel(null, "8", "dsadsa", "fsafasf"),
            ItemsModel(null, "9", "dsadsa", "fsafasf"),

            )
        ItemsList(Modifier, list)
    }
}

@Composable
fun SelectCustomer(
    content: String = "select customer",
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(20)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .border(shape = shape, width = 1.dp, color = Color.Black)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        Image(painter = painterResource(id = R.drawable.image_search_24), contentDescription = "")
        Text(
            text = content,
            color = Color.Black,
            fontWeight = FontWeight.W300,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
        )
    }
}


@Preview
@Composable
fun NewInvoiceScreenPreview() {
    InvoiceInfoScreen(onNavButtonClick = {})
}