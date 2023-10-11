package com.endcodev.myinvoice.ui.screens.customers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.data.model.CustomerUiState
import com.endcodev.myinvoice.ui.viewmodels.CustomerInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerInfoScreen(
    viewModel: CustomerInfoViewModel = hiltViewModel(),
    onAcceptClick: (CustomerModel) -> Unit,
    onCancelClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { },
        content = { innerPadding ->
            CustomerContent(innerPadding, uiState, viewModel)
        },
        bottomBar = {
            BottomButtons(uiState.isAcceptEnabled, onAcceptClick, onCancelClick)
        }
    )
}

@Composable
fun CustomerContent(
    innerPadding: PaddingValues,
    uiState: CustomerUiState,
    viewModel: CustomerInfoViewModel
) {

    Column(
        Modifier
            .padding(innerPadding)
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
        ) {
            val (title, nif, image) = createRefs()

            CustomerTitle(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(image.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(image.start)
                    width = Dimension.preferredWrapContent
                }
            )

            CompanyIdNum(uiState.cIdentifier, onTextChanged = {
                viewModel.onDataChanged(
                    identifier = it,
                    fiscalName = uiState.cFiscalName,
                    telephone = uiState.cTelephone
                )
            },
                modifier = Modifier.constrainAs(nif) {
                    top.linkTo(title.bottom, margin = 4.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(title.end)
                    width = Dimension.preferredWrapContent
                }
            )

            ImageCustomer(modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top, margin = 4.dp)
                start.linkTo(nif.end, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
            })
        }

        CompanyName(uiState.cFiscalName, onTextChanged = {
            viewModel.onDataChanged(
                identifier = it,
                fiscalName = uiState.cFiscalName,
                telephone = uiState.cTelephone
            )
        }
        CompanyEmail()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyIdNum(idNum: String, onTextChanged: (String) -> Unit, modifier: Modifier) {

    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = idNum,
        onValueChange = { onTextChanged(it) },
        label = { Text("Company ID") }
    )
}

@Composable
fun CustomerTitle(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "Customer Info",
        color = Color.Black,
        fontSize = 26.sp,
        textAlign = TextAlign.Start,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyName(cFiscalName: String, onTextChanged: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = cFiscalName,
        onValueChange = { onTextChanged(it) },
        label = { Text("Company name") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyEmail() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text("Email") }
    )
}

@Composable
fun ImageCustomer(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.person_24),
        contentDescription = "logo",
        modifier = modifier
            .width(150.dp)
            .height(140.dp)
    )
}

@Composable
fun BottomButtons(
    enabled: Boolean,
    onAcceptClick: (CustomerModel) -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        MyButton("Cancel", Modifier.weight(1F), true)
        Spacer(modifier = Modifier.width(25.dp))
        MyButton("Accept", Modifier.weight(1F), enabled)
    }
}

@Composable
fun MyButton(text: String, modifier: Modifier, enabled: Boolean) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier.height(50.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(text = text)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelection(modifier: Modifier) {
    val countryList = listOf(
        "United state",
        "Australia",
        "Japan",
        "India",
    )
    val text = remember { mutableStateOf("") } // initial value
    val isOpen = remember { mutableStateOf(false) } // initial value
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
    }
    Box(modifier = modifier) {
        Column {
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text(text = "United state") },
                modifier = Modifier.fillMaxWidth()
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = countryList,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { isOpen.value = true }
                )
        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                },
                text = {
                    Text(
                        it, modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.Start)
                    )
                }
            )
        }
    }
}


@Preview
@Composable
fun PreviewDetailsCustomer() {
    //CustomerInfoScreen(onAcceptClick = {}, onCancelClick = {})
}