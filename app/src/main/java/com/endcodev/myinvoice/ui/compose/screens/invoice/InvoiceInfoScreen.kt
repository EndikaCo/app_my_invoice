package com.endcodev.myinvoice.ui.compose.screens.invoice

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.endcodev.myinvoice.ui.compose.components.BottomButtons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceInfoScreen(
    //viewModel: CustomerInfoViewModel = hiltViewModel(),
    onNavButtonClick: () -> Unit
) {

    Scaffold(
        topBar = { },
        content = { innerPadding ->
            InvoiceContent(innerPadding)
        },
        bottomBar = { BottomButtons(enabled = true, onAcceptClick = { /*TODO*/ }) {
            
        }}
    )
}

fun now(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceContent(innerPadding: PaddingValues) {
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
        SelectCustomer()
    }
}

@Composable
fun SelectCustomer(
    content: String = "select customer",
    contentColor: Color = Color.LightGray,
    borderColor: Color = Color.Black
) {
    val shape = RoundedCornerShape(20)
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .border(shape = shape, width = 1.dp, color = borderColor)
            ,
        shape = shape
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
        ) {
            Text(
                text = content,
                color = Color.Black,
                fontWeight = FontWeight.W300,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
            )
        }
    }
}

@Composable
fun CustomDialog() {
    Dialog(onDismissRequest = { }) {
        CustomDialogUI()
    }
}

@Composable
fun CustomDialogUI() {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Text(
            text = "dssdadsad",
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