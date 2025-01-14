package com.endcodev.myinvoice.presentation.compose.screens.details

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.colorResource
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
import com.endcodev.myinvoice.domain.models.invoice.SaleItem
import com.endcodev.myinvoice.domain.models.invoice.getDate
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.presentation.compose.components.DocSelection
import com.endcodev.myinvoice.presentation.compose.components.MyBottomBar
import com.endcodev.myinvoice.presentation.compose.components.MyDatePicker
import com.endcodev.myinvoice.presentation.compose.components.SwipeToDeleteContainer
import com.endcodev.myinvoice.presentation.compose.components.filteredImage
import com.endcodev.myinvoice.presentation.compose.dialogs.ChooseCustomerDialogActions
import com.endcodev.myinvoice.presentation.compose.dialogs.InvoiceProductAddDialogActions
import com.endcodev.myinvoice.presentation.compose.dialogs.ProductDialog
import com.endcodev.myinvoice.presentation.compose.screens.details.MyObject.NULL_ITEM
import com.endcodev.myinvoice.presentation.navigation.Routes
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme
import com.endcodev.myinvoice.presentation.viewmodels.InvoiceInfoViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object MyObject {
    const val NULL_ITEM = -1
}

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
        onCustomerChange = viewModel::updateCustomer,
        onDeleteButton = {
            viewModel.deleteInvoice()
            navController.navigate(Routes.InvoicesContent.routes)
        },
        onDateChanged = { viewModel.setDate(it) },
        onSaleChanged = { viewModel.addSale(it) },
        onSwipeDelete = { viewModel.deleteSale(it) }
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
    onSwipeDelete: (SaleItem) -> Unit
) {


    val state = rememberDatePickerState()
    val dateDialog = remember { mutableStateOf(false) } //show DatePicker dialog
    val customerDialog = remember { mutableStateOf(false) } //show CustomerSelect dialog
    val productDialog = remember { mutableIntStateOf(-1) }
    val priceDialog = remember { mutableIntStateOf(-1) }

    if (dateDialog.value)
        MyDatePicker(
            openDialog = { dateDialog.value = it },
            state = state,
            newDate = {
                val instant = Instant.ofEpochMilli(it.selectedDateMillis ?: (0L))
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                onDateChanged(formatter.format(date))
            }
        )


    if (productDialog.intValue != NULL_ITEM) {
        InvoiceProductAddDialogActions(
            onDialogAccept = { product ->
                onSaleChanged(
                    SaleItem(
                        id = productDialog.intValue,
                        product = product,
                        price = product.price,
                        quantity = 1F,
                        discount = 0
                    )
                )
                productDialog.intValue = -1
            },
            onDialogCancel = {
                productDialog.intValue = -1
            },
        )
    }

    if (priceDialog.intValue != -1)
        ProductDialog(
            sale = uiState.invoice.saleList[priceDialog.intValue],
            onDialogAccept = {
                onSaleChanged(it)
                priceDialog.intValue = NULL_ITEM
            },
            onDialogCancel = {
                priceDialog.intValue = NULL_ITEM
                             },
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
                    onPricesClick = { priceDialog.intValue = it },
                    onSwipeDelete = onSwipeDelete
                )
            },
            bottomBar = {
                Column {
                    TotalsRow(uiState.invoice)
                    HorizontalDivider(
                        modifier = Modifier
                            .background(colorResource(R.color.transparent))
                            .height(1.dp)
                            .fillMaxWidth()
                    )
                    MyBottomBar(
                        enableDelete = uiState.isDeleteEnabled,
                        enableSave = uiState.isSaveEnabled,
                        addItemVisible = true,
                        onAcceptClick = onAcceptButton,
                        onAddItemClick = {
                            productDialog.intValue = uiState.invoice.saleList.size
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
    onPricesClick: (Int) -> Unit,
    onSwipeDelete: (SaleItem) -> Unit
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
            InvoiceNum(invoiceId = uiState.invoice.id.toString())
            Spacer(modifier = Modifier.width(8.dp))
            InvoiceDate(uiState.invoice.date, onClick = { onDateClick() })
            Spacer(modifier = Modifier.width(8.dp))
            DocSelection(onSelection = { }, docs = listOf("Invoice", "Receipt"))
        }
        SelectCustomer(
            customer = uiState.invoice.customer,
            onIconClick = { onCustomerClick() })
        Spacer(modifier = Modifier.height(16.dp))
        InvoiceItemsList(uiState.invoice.saleList, onProductClick, onPricesClick, onSwipeDelete)
    }
}

@Composable
fun InvoiceItemsList(
    salesList: List<SaleItem>,
    onProductClick: (Int) -> Unit,
    onPricesClick: (Int) -> Unit,
    onSwipeDelete: (SaleItem) -> Unit
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            itemsIndexed(salesList) { index, sale ->

                SwipeToDeleteContainer(
                    item = sale,
                    onDelete = {
                        onSwipeDelete(it)
                    }
                ) {
                    InvoiceProduct2(
                        onProductClick = {
                            onProductClick(index)
                        },
                        onPricesClick = {
                            onPricesClick(index)
                        },
                        sale
                    )
                }
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
        filteredImage(itemSaleItem?.product?.image, painterResource(id = R.drawable.no_photo_24))
    val total =
        (itemSaleItem!!.quantity * itemSaleItem.price) * (1 - itemSaleItem.discount / 100)

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
                Text(text = itemSaleItem.product.name, maxLines = 1, fontSize = 12.sp)


                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = itemSaleItem.product.id,
                    modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    fontSize = 12.sp
                )
            }
            Row(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "${itemSaleItem.quantity}u",
                    modifier = Modifier.width(80.dp),
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "${itemSaleItem.price}€",
                    modifier = Modifier.width(80.dp),
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "${itemSaleItem.discount}%",
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
fun TotalsRow(invoice: Invoice) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally // Add this line
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Subtotal", fontSize = 12.sp, textAlign = TextAlign.Center)
                Text(text = "${invoice.iSubtotal}€", fontSize = 16.sp, textAlign = TextAlign.Center)
            }
            Column {
                Text(text = "Tax", fontSize = 12.sp, textAlign = TextAlign.Center)
                Text(text = "${invoice.iTaxes}€", fontSize = 16.sp, textAlign = TextAlign.Center)
            }
            Column {
                Text(text = "Discount", fontSize = 12.sp, textAlign = TextAlign.Center)
                Text(text = "${invoice.iDiscount}€", fontSize = 16.sp, textAlign = TextAlign.Center)
            }
            Column {
                Text(text = "Total", fontSize = 12.sp, textAlign = TextAlign.Center)
                Text(text = "${invoice.iTotal}€", fontSize = 16.sp, textAlign = TextAlign.Center)
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
            text = customer.fiscalName,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
            textAlign = TextAlign.Center,
            color = Color.Black
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
            invoice = Invoice(
                id = null,
                date = getDate(),
                customer = Customer(null, "Select Customer", "Select Customer"),
                reference = "",
                saleList = mutableListOf(
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
            ),
        )

        InvoiceInfoScreen(
            onAcceptButton = {},
            uiState = uiState,
            onCustomerChange = {},
            onDeleteButton = {},
            onDateChanged = {},
            onSaleChanged = {},
            onSwipeDelete = {}
        )
    }
}
