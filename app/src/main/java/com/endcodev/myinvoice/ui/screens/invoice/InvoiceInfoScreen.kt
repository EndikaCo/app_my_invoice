package com.endcodev.myinvoice.ui.screens.invoice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.endcodev.myinvoice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceInfoScreen() {

    Scaffold(
        topBar = { },
        content = { innerPadding ->
            InvoiceContent(innerPadding)
        }
    )
}

@Composable
fun InvoiceContent(innerPadding: PaddingValues) {

}

@Preview
@Composable
fun NewInvoiceScreenPreview() {
    InvoiceInfoScreen()
}