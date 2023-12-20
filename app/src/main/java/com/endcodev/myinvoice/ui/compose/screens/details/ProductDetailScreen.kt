package com.endcodev.myinvoice.ui.compose.screens.details

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.domain.models.product.ProductUiState
import com.endcodev.myinvoice.ui.compose.components.MyBottomBar
import com.endcodev.myinvoice.ui.compose.components.filteredImage
import com.endcodev.myinvoice.ui.compose.components.uriToPainterImage
import com.endcodev.myinvoice.ui.navigation.Routes
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.ItemInfoViewModel

@Composable
fun ProductsDetailScreenActions(
    itemId: String?,
    navController: NavHostController,
) {
    val viewModel: ItemInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    itemId?.let { LaunchedEffect(it) { viewModel.getItem(it) } }

    fun onUpdateData(
        code: String? = null,
        name: String? = null,
        image: Uri? = null,
        type: String? = null,
        cost: Float? = null,
        price: Float? = null
    ) {
        viewModel.onDataChanged(
            code = code ?: uiState.iCode,
            name = name ?: uiState.iName,
            image = image ?: uiState.iImage,
            type = type ?: uiState.iType,
            cost = cost ?: uiState.iCost,
            price = price ?: uiState.iPrice
        )
    }

    ProductsDetailScreen(
        onAcceptButton = {
            viewModel.saveItem()
            navController.navigate(Routes.ItemsContent.routes)
        },
        uiState = uiState,
        onCodeChanged = { onUpdateData(code = it) },
        onNameChanged = { onUpdateData(name = it) },
        onUriChanged = { onUpdateData(image = it) },
        onTypeChanged = { onUpdateData(type = it) },
        onCostChanged = { onUpdateData(cost = it) },
        onPriceChanged = { onUpdateData(price = it) },
        onDeleteButton = {
            viewModel.deleteItem()
            navController.navigate(Routes.ItemsContent.routes)
        }
    )
}

@Composable
fun ProductsDetailScreen(
    onAcceptButton: () -> Unit,
    onDeleteButton: () -> Unit,
    uiState: ProductUiState,
    onCodeChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onUriChanged: (Uri) -> Unit,
    onTypeChanged: (String) -> Unit,
    onCostChanged: (Float) -> Unit,
    onPriceChanged: (Float) -> Unit,
) {
    Scaffold(
        topBar = { },
        content = { innerPadding ->
            ItemsInfoContent(
                innerPadding,
                uiState,
                onCodeChanged,
                onNameChanged,
                onUriChanged,
                onTypeChanged,
                onCostChanged,
                onPriceChanged
            )
        },
        bottomBar = {

            MyBottomBar(
                enableDelete = uiState.isAcceptEnabled,
                enableSave = true,
                addItemVisible = false,
                onAcceptClick = onAcceptButton,
                onAddItemClick = {},
                onDeleteClick = onDeleteButton,
            )
        }
    )
}

@Composable
fun ItemsInfoContent(
    innerPadding: PaddingValues,
    uiState: ProductUiState,
    onCodeChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onUriChanged: (Uri) -> Unit,
    onTypeChanged: (String) -> Unit,
    onCostChanged: (Float) -> Unit,
    onPriceChanged: (Float) -> Unit,
) {

    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->

            if (uri == null)
                Log.e("SinglePhotoPickerLauncher", "Image not valid")
            else {
                // Grant read permission to the obtained URI
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)
                onUriChanged(uri)
            }
        }
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
    ) {
        Text(
            text = "Product Info",
            modifier = Modifier.padding(start = pPadding, end = pPadding, top = pPadding),
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier.padding(start = pPadding, end = pPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.width(230.dp),
                verticalArrangement = Arrangement.Center
            ) {
                IdNum(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    idNum = uiState.iCode,
                    onTextChanged = { onCodeChanged(it) },
                )
                ItemName(
                    label = "Item type",
                    cFiscalName = uiState.iType,
                    onTextChanged = { onTypeChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                )
            }
            ItemInfoImage(
                singlePhotoPickerLauncher = singlePhotoPickerLauncher,
                cImage = uiState.iImage,
                defaultImage = painterResource(id = R.drawable.no_photo_24)
            )
        }
        Column(modifier = Modifier.padding(start = pPadding, end = pPadding)) {

            ItemName(
                label = "Item name",
                cFiscalName = uiState.iName,
                onTextChanged = { onNameChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )

            ItemCost(
                label = "Item cost",
                amount = uiState.iCost,
                onAmountChanged = { onCostChanged(it) }
            )
            ItemCost(
                label = "Item price",
                amount = uiState.iPrice,
                onAmountChanged = { onPriceChanged(it) }
            )
            ItemStock(
                label = "Item stock",
                amount = uiState.iStock,
                onAmountChanged = { onPriceChanged(it) }
            )
        }
    }
}

/**
 * Displays a item's image or new image from photo picker launcher.
 * @param singlePhotoPickerLauncher The launcher used to pick a visual media (image) from the device.
 * @param cImage The current image URI of the customer.
 */
@Composable
fun ItemInfoImage(
    singlePhotoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    cImage: Uri?,
    defaultImage: Painter
) {
    val image = filteredImage(cImage, defaultImage)

    Box(
        modifier = Modifier
            .height(130.dp) // Size of the Box (background)
            .width(150.dp),
        contentAlignment = Alignment.Center, // Center content in the Box

    ) {
        Image(
            painter = image.image,
            contentDescription = "customer Image",
            modifier = Modifier
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                .fillMaxSize()
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            colorFilter = image.filter
        )
    }
}

@Composable
fun IdNum(modifier: Modifier, idNum: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = modifier,
        value = idNum,
        onValueChange = { onTextChanged(it) },
        label = { Text("Product code") }
    )
}

@Composable
fun ItemName(
    modifier: Modifier,
    label: String,
    cFiscalName: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = cFiscalName,
        onValueChange = { onTextChanged(it) },
        label = { Text(label) }
    )
}

@Composable
fun ItemCost(label: String, amount: Float, onAmountChanged: (Float) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.width(100.dp),
        value = amount.toString(),
        onValueChange = { onAmountChanged(it.toFloat()) },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
    )
}

@Composable
fun ItemStock(label: String, amount: Float, onAmountChanged: (Float) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.width(100.dp),
        value = amount.toString(),
        onValueChange = { onAmountChanged(it.toFloat()) },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
    )
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCustomerInfoScreen() {
    MyInvoiceTheme {
        ProductsDetailScreen(
            onAcceptButton = {},
            onDeleteButton = {},
            uiState = ProductUiState(),
            onNameChanged = {},
            onCodeChanged = {},
            onUriChanged = {},
            onTypeChanged = {},
            onCostChanged = {},
            onPriceChanged = {},
        )
    }
}
