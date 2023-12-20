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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
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
import com.endcodev.myinvoice.domain.models.invoice.Invoice
import com.endcodev.myinvoice.domain.models.invoice.InvoiceUiState
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.invoice.getDate
import com.endcodev.myinvoice.ui.compose.components.MyDatePicker
import com.endcodev.myinvoice.ui.compose.components.DocSelection
import com.endcodev.myinvoice.ui.compose.components.MyBottomBar
import com.endcodev.myinvoice.ui.compose.components.filteredImage
import com.endcodev.myinvoice.ui.compose.dialogs.ChooseCustomerDialogActions
import com.endcodev.myinvoice.ui.compose.dialogs.InvoiceProductAddDialogActions
import com.endcodev.myinvoice.ui.compose.dialogs.ProductDialog
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
        onSaleChanged = { viewModel.addSale(it) },
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
    onSaleChanged: (SaleItem) -> Unit,
) {

    val state = rememberDatePickerState()
    val dateDialog = remember { mutableStateOf(false) } //show DatePicker dialog
    val customerDialog = remember { mutableStateOf(false) } //show CustomerSelect dialog
    val productDialog = remember { mutableIntStateOf(-1) }
    val priceDialog = remember { mutableStateOf(false) }

    if (dateDialog.value)
        MyDatePicker(
            openDialog = { dateDialog.value = it },
            state,
            newDate = {
                val instant = Instant.ofEpochMilli(it.selectedDateMillis ?: (0L))
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                onDateChanged(formatter.format(date))
            }
        )

    if (productDialog.intValue != -1) {
        InvoiceProductAddDialogActions(
            onDialogAccept = { product ->
                onSaleChanged(
                    SaleItem(
                        sId = productDialog.intValue,
                        sProduct = product,
                        sPrice = product.iPrice,
                        sQuantity = 1F,
                        sDiscount = 0
                    )
                )
                productDialog.intValue = -1
            },
            onDialogCancel = {
                productDialog.intValue = -1
            },
        )
    }

    if (priceDialog.value)
        ProductDialog(
            sale = SaleItem(
                sId = 1,
                sProduct = Product(
                    null,
                    "PRO-3121",
                    "233",
                    "das",
                    "",
                    12.00F,
                    12.00F,
                    12.00F,

                    ),
                31F,
                sQuantity = 12F,
                sDiscount = 10
            ),
            onDialogAccept = {}, //todo
            onDialogCancel = { priceDialog.value = false },
        )

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
                    onProductClick = { productDialog.intValue = it },
                    onPricesClick = { priceDialog.value = true }
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
                    MyBottomBar(
                        enableDelete = true,
                        enableSave = true,
                        addItemVisible = true,
                        onAcceptClick = onAcceptButton,
                        onAddItemClick = {
                            productDialog.intValue = uiState.invoice.iSaleList.size
                        },
                        onDeleteClick = onDeleteButton,
                    )
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
    onProductClick: (Int) -> Unit,
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
            InvoiceDate(uiState.invoice.iDate, onClick = { onDateClick() })
            Spacer(modifier = Modifier.width(8.dp))
            DocSelection(onSelection = { }, docs = listOf("Invoice", "Receipt"))
        }
        SelectCustomer(
            customer = uiState.invoice.iCustomer,
            onIconClick = { onCustomerClick() })
        Spacer(modifier = Modifier.height(16.dp))
        InvoiceItemsList(uiState.invoice.iSaleList, onProductClick, onPricesClick)
    }
}

@Composable
fun InvoiceItemsList(
    salesList: List<SaleItem>,
    onProductClick: (Int) -> Unit,
    onPricesClick: () -> Unit
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            itemsIndexed(salesList) { index, sale ->
                InvoiceProduct2(
                    onProductClick = {
                        onProductClick(index + 1)
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
fun InvoiceProduct2(
    onProductClick: () -> Unit,
    onPricesClick: () -> Unit,
    itemSaleItem: SaleItem?
) {
    val image =
        filteredImage(itemSaleItem?.sProduct?.iImage, painterResource(id = R.drawable.no_photo_24))
    val total =
        (itemSaleItem!!.sQuantity * itemSaleItem.sPrice) * (1 - itemSaleItem.sDiscount / 100)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.clickable { onProductClick() }) {
            ListImage(
                image = image.image,
                colorFilter = image.filter
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onPricesClick() }) {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = itemSaleItem.sProduct.iName, maxLines = 1, fontSize = 12.sp)


                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = itemSaleItem.sProduct.iCode,
                    modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    fontSize = 12.sp
                )
            }
            Row(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "${itemSaleItem.sQuantity}u",
                    modifier = Modifier.width(80.dp),
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "${itemSaleItem.sPrice}€",
                    modifier = Modifier.width(80.dp),
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "${itemSaleItem.sDiscount}%",
                    modifier = Modifier.width(60.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${total}€",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ListImage(image: Painter, colorFilter: ColorFilter?) {
    Box(
        modifier = Modifier
            .size(35.dp) // Size of the Box (background)
            .border(
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
                shape = RectangleShape
            ), contentAlignment = Alignment.Center // Center content in the Box
    ) {
        Image(
            painter = image,
            contentDescription = "customer Image",
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
                .clip(RectangleShape),
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
        modifier = Modifier.width(85.dp),
        readOnly = true
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
        modifier = Modifier.width(160.dp),
        readOnly = true
    )
}

@Composable
fun SelectCustomer(
    customer: Customer,
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onIconClick() }
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .border(
                shape = RoundedCornerShape(10),
                width = 1.dp,
                color = MaterialTheme.colorScheme.background
            )
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_search_24),
            contentDescription = "",
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(Modifier.weight(0.6f))

        Text(
            text = customer.cFiscalName,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.background
        )

        Spacer(Modifier.weight(1f))
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewInvoiceInfoScreen() {
    MyInvoiceTheme {

        val uiState = InvoiceUiState(
            false,
            Invoice(
                iId = null,
                iDate = getDate(),
                iCustomer = Customer(null, "Select Customer", "Select Customer"),
                iReference = "",
                iTotal = 0f,
                iSaleList = listOf(
                    SaleItem(
                        0,
                        Product(null, "AE3235", "LED GU10 6W", "", "", 12F, 1F, 12.00F),
                        222.45f,
                        54f,
                        10
                    ),
                    SaleItem(
                        1,
                        Product(null, "HH324", "HT-05 263123", "", "", 6F, 8F, 4.10F),
                        12.5f,
                        3f,
                        0
                    ),
                )
            )
        )

        InvoiceInfoScreen(
            onAcceptButton = {},
            uiState = uiState,
            onCustomerChange = {},
            onDeleteButton = {},
            onDateChanged = {},
            onSaleChanged = {},
        )
    }
}
