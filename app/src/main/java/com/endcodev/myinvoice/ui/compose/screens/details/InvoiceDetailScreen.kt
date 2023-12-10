package com.endcodev.myinvoice.ui.compose.screens.details

import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import com.endcodev.myinvoice.domain.models.customer.Customer
import com.endcodev.myinvoice.domain.models.invoice.InvoiceUiState
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.ui.compose.components.ActionButtons
import com.endcodev.myinvoice.ui.compose.components.CDatePicker
import com.endcodev.myinvoice.ui.compose.components.DocSelection
import com.endcodev.myinvoice.ui.compose.dialogs.ChooseCustomerDialogActions
import com.endcodev.myinvoice.ui.compose.dialogs.InvoiceProductAddDialogActions
import com.endcodev.myinvoice.ui.navigation.Routes
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.InvoiceInfoViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
        onDeleteButton = { viewModel.deleteInvoice() },
        onDateChanged = { viewModel.setDate(it) },
        onProductChanged = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceInfoScreen(
    onAcceptButton: () -> Unit,
    onDeleteButton: () -> Unit,
    uiState: InvoiceUiState,
    onCustomerChange: (Customer) -> Unit,
    onDateChanged: (String) -> Unit,
    onProductChanged: (Product) -> Unit
) {

    val state = rememberDatePickerState()
    val dateDialog = remember { mutableStateOf(false) } //show DatePicker dialog
    val customerDialog = remember { mutableStateOf(false) } //show CustomerSelect dialog
    val productDialog = remember { mutableStateOf(false) }

    if (dateDialog.value)
        CDatePicker(
            openDialog = { dateDialog.value = it },
            state,
            newDate = {
                val instant = Instant.ofEpochMilli(it.selectedDateMillis ?: (0L))
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                onDateChanged(formatter.format(date))
            })

    if (productDialog.value)
        InvoiceProductAddDialogActions(
            onDialogAccept = {
                productDialog.value = false
                onProductChanged(it)
            },
            onDialogCancel = {
                productDialog.value = false
            })

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
                    uiState = uiState,
                    onProductClick = {},
                    onPricesClick = {}
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
    uiState: InvoiceUiState,
    onProductClick: () -> Unit,
    onPricesClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            InvoiceNum(invoiceId = uiState.invoice.iId.toString())
            Spacer(modifier = Modifier.width(8.dp))
            InvoiceDate(uiState.invoice.iDate, onClick = { onDateClick() }) //todo
            Spacer(modifier = Modifier.width(8.dp))
            DocSelection(onSelection = { }, docs = listOf("Invoice", "Receipt"))
        }
        SelectCustomer(
            customer = uiState.invoice.iCustomer,
            onIconClick = { onCustomerClick() })
        Spacer(modifier = Modifier.height(16.dp))
        InvoiceItemsList(salesList, onProductClick, onPricesClick)
    }
}

val salesList =listOf(SaleItem(
    Product(null, "PRO-3121", "233", "das", ""), 31F,
    12F, 10
))
@Composable
fun InvoiceItemsList(salesList : List<SaleItem> ,onProductClick: () -> Unit, onPricesClick: () -> Unit) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(salesList) { sale ->
                InvoiceProduct(
                    onCustomerClick = {
                        onProductClick()
                    },
                    onPricesClick = {
                        onPricesClick()
                    },
                    sale
                )
            }
        })
}

@Composable
fun InvoiceProduct(onCustomerClick: () -> Unit, onPricesClick: () -> Unit, itemSaleItem: SaleItem?) {

    Row(){
        Row(
            modifier = Modifier
                .clickable { onCustomerClick() } //todo
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            //todo first box
            val colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
            val image = painterResource(id = R.drawable.filter_24)

            Spacer(modifier = Modifier.width(16.dp))
            ListImage(
                image = image,
                colorFilter = colorFilter
            )
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk(itemSaleItem!!.sProduct.iCode, "ref") //Todo
        }

        //todo second box
        Row(
            modifier = Modifier
                .clickable { onPricesClick() } //todo
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk(itemSaleItem!!.sQuantity.toString(), "pc")
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk(itemSaleItem.sPrice.toString(), "(eur)")
            Spacer(modifier = Modifier.width(16.dp))
            ColumnDesk(itemSaleItem.sDiscount.toString(), "%")
            Spacer(modifier = Modifier.width(16.dp))

            val total = (itemSaleItem.sPrice * itemSaleItem.sQuantity) * (1 - itemSaleItem.sDiscount / 100)
            ColumnDesk(total.toString(), "EUR")}
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
    customer: Customer,
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
            onDeleteButton = {},
            onDateChanged = {},
            onProductChanged = {}
        )
    }
}
