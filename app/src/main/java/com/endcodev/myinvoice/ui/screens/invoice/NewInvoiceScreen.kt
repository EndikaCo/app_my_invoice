package com.endcodev.myinvoice.ui.screens.invoice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.endcodev.myinvoice.R

@Composable
fun NewInvoiceScreen() {

    Column {
        UpPArt()
        CompanyName()
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 4.dp))
        CompanyCp()
    }
}

@Composable
fun UpPArt() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (nif, cid, image) = createRefs()

        CompanyIdNum(
            modifier = Modifier.constrainAs(cid) {
                top.linkTo(image.top, margin = 4.dp)
                start.linkTo(parent.start, margin = 20.dp)
                width = Dimension.preferredWrapContent
            }
        )

        CompanyIdNum(
            modifier = Modifier.constrainAs(nif) {
                top.linkTo(cid.bottom, margin = 4.dp)
                start.linkTo(parent.start, margin = 20.dp)
                width = Dimension.preferredWrapContent
            }
        )

        ImageCustomer(modifier = Modifier.constrainAs(image) {
            top.linkTo(cid.top, margin = 4.dp)
            start.linkTo(nif.end, margin = 20.dp)
            end.linkTo(parent.end, margin = 20.dp)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyIdNum(modifier: Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(modifier = modifier.width(200.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyName() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text("Company name") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyCp() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text("address") }
    )
}

@Composable
fun ImageCustomer(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.person_24),
        contentDescription = "logo",
        modifier = modifier
            .width(150.dp)
            .height(150.dp)
    )
}

@Preview
@Composable
fun NewInvoiceScreenPreview() {
    NewInvoiceScreen()
}