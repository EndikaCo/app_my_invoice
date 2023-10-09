package com.endcodev.myinvoice.ui.screens.customers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.screens.auth.ImageLogo

@Composable
fun DetailsCustomer(){

    //constrait layout


    Column {
        Row {
            Column {
                SimpleOutlinedTextFieldSample()
                SimpleOutlinedTextFieldSample()
            }
            ImageCustomer(modifier = Modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

@Composable
fun ImageCustomer(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.invoice_logo),
        contentDescription = "logo",
        modifier = modifier
            .height(200.dp)
            .width(200.dp)
    )
}

@Preview
@Composable
fun PreviewDetailsCustomer(){
    DetailsCustomer()
}