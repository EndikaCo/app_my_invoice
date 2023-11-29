package com.endcodev.myinvoice.ui.compose.screens.details

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.domain.models.CustomerModel
import com.endcodev.myinvoice.domain.models.InvoiceUiState
import com.endcodev.myinvoice.domain.models.ItemModel
import com.endcodev.myinvoice.ui.compose.components.ActionButtons
import com.endcodev.myinvoice.ui.compose.components.CDatePicker
import com.endcodev.myinvoice.ui.compose.components.DocSelection
import com.endcodev.myinvoice.ui.compose.dialogs.ChooseCustomerDialogActions
import com.endcodev.myinvoice.ui.navigation.Routes
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.InvoiceInfoViewModel

@Composable
fun InvoiceDetailActions(
    invoiceId: String?,
    navController: NavHostController
) {
    val viewModel: InvoiceInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (invoiceId != null) {
        LaunchedEffect(invoiceId) {
            viewModel.getInvoice(invoiceId)
        }
    }

    InvoiceInfoScreen(
        onAcceptButton = {
            viewModel.saveInvoice()
            navController.navigate(Routes.InvoicesContent.routes)
        },
        uiState = uiState,
        onCustomerChange = viewModel::setCustomer,
        onCancelButton = { navController.popBackStack() },
        onDeleteButton = { viewModel.deleteInvoice() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceInfoScreen(
    onAcceptButton: () -> Unit,
    onCancelButton: () -> Unit,
    onDeleteButton: () -> Unit,
    uiState: InvoiceUiState,
    onCustomerChange: (CustomerModel) -> Unit
) {

    val state = rememberDatePickerState()
    val dateDialog = remember { mutableStateOf(false) }
    val customerDialog = remember { mutableStateOf(false) }

    if (dateDialog.value)
        CDatePicker(
            openDialog = { dateDialog.value = it },
            state,
            newDate = { Log.v("new date", it.selectedDateMillis.toString()) })

    if (customerDialog.value)
        ChooseCustomerDialogActions(
            onDismissRequest = { customerDialog.value = false },
            onAcceptRequest = {
                onCustomerChange(it)
                customerDialog.value = false
            },
        )
    else
        Scaffold(
            topBar = { },
            content = { innerPadding ->
                InvoiceInfoContent(
                    innerPadding = innerPadding,
                    onDateClick = { dateDialog.value = true },
                    onCustomerClick = { customerDialog.value = true },
                    uiState = uiState
                )
            },
            bottomBar = {
                Column {
                    Divider(
                        modifier = Modifier
                            .background(Color(R.color.transparent))
                            .height(1.dp)
                            .fillMaxWidth()
                    )
                    ActionButtons(
                        enabled = true,
                        onAcceptClick = {
                            onAcceptButton()
                        },
                        onDeleteClick = {
                            onDeleteButton()
                        })
                }
            }
        )
}

@Composable
fun InvoiceInfoContent(
    innerPadding: PaddingValues,
    onDateClick: () -> Unit,
    onCustomerClick: () -> Unit,
    uiState: InvoiceUiState
) {
    var mCustomer: CustomerModel? = uiState.customer
    if (mCustomer == null)
        mCustomer = CustomerModel(cImage = null, cFiscalName = "New Customer", cIdentifier = "-")

    Column(
        modifier = Modifier.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            InvoiceNum(invoiceId = uiState.id)
            Spacer(modifier = Modifier.width(8.dp))
            InvoiceDate("", onClick = { onDateClick() }) //todo
            Spacer(modifier = Modifier.width(8.dp))
            DocSelection(onSelection = { }, docs = listOf("Invoice", "Receipt"))
        }
        SelectCustomer(customer = mCustomer, onIconClick = { onCustomerClick() })
        Spacer(modifier = Modifier.height(16.dp))
        InvoiceItemsList()
    }
}

@Composable
fun InvoiceItemsList() {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally)
    {
        items(10) {
            InvoiceProduct(onItemClick = {}, null)
        }
    }
}

@Composable
fun InvoiceProduct(onItemClick: () -> Unit, product: ItemModel?) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 8.dp, start = 15.dp, end = 15.dp) //between items
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            val colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
            val image = painterResource(id = R.drawable.filter_24)

            Spacer(modifier = Modifier.width(16.dp))
            ListImage(
                image = image,
                colorFilter = colorFilter
            )
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk("PRO-3121", "ref")
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk("233", "pc")
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk("0,36", "(eur)")
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk("50", "%")
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk("44,36", "EUR")
        }
    }
}

@Composable
fun ColumnDesk(topText: String, bottomDesc: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = topText, textAlign = TextAlign.Center, fontSize = 16.sp)
        Text(text = bottomDesc, textAlign = TextAlign.Center, fontSize = 9.sp)
    }
}

@Composable
fun ListImage(image: Painter, colorFilter: ColorFilter?) {

    Box(
        modifier = Modifier
            .size(35.dp) // Size of the Box (background)
            .border(
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
                shape = CircleShape
            ), contentAlignment = Alignment.Center // Center content in the Box
    ) {
        Image(
            painter = image,
            contentDescription = "customer Image",
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter
        )
    }
}

@Composable
fun InvoiceNum(invoiceId: String) {
    OutlinedTextField(
        value = invoiceId,
        onValueChange = { },
        label = { Text(text = "invoice") },
        modifier = Modifier.width(85.dp)
    )
}

@Composable
fun InvoiceDate(date: String, onClick: () -> Unit, dateChanged: (String) -> Unit = {}) {

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
        onValueChange = { dateChanged(it) },
        label = { Text(text = "date") },
        modifier = Modifier.width(160.dp)

    )
}

@Composable
fun SelectCustomer(
    customer: CustomerModel,
    onIconClick: () -> Unit
) {
    val shape = RoundedCornerShape(20)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .border(shape = shape, width = 1.dp, color = MaterialTheme.colorScheme.onBackground)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        ) {
        Image(
            painter = painterResource(id = R.drawable.image_search_24),
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.clickable { onIconClick() }
        )

        Text(
            text = customer.cFiscalName,
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
        InvoiceInfoScreen(
            onAcceptButton = {},
            uiState = InvoiceUiState(),
            onCustomerChange = {},
            onCancelButton = {},
            onDeleteButton = {}
        )
    }
}