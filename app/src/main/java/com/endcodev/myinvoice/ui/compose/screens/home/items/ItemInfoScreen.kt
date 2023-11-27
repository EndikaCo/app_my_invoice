package com.endcodev.myinvoice.ui.compose.screens.home.items

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.domain.models.ItemUiState
import com.endcodev.myinvoice.ui.compose.components.AcceptCancelButtons
import com.endcodev.myinvoice.ui.compose.screens.home.customers.details.pPadding
import com.endcodev.myinvoice.ui.compose.uriToPainterImage
import com.endcodev.myinvoice.ui.navigation.Routes
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.ItemInfoViewModel

@Composable
fun ItemDetailActions(
    itemId: String?,
    navController: NavHostController,
) {
    val viewModel: ItemInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (itemId != null) {
        LaunchedEffect(itemId) { viewModel.getItem(itemId) }
    }

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

    ItemInfoScreen(
        onAcceptButton = {
            viewModel.saveItem()
            navController.navigate(Routes.ItemsContent.routes)
        },
        onCancelButton = { navController.navigate(Routes.ItemsContent.routes) },
        uiState = uiState,
        onCodeChanged = { onUpdateData(code = it) },
        onNameChanged = { onUpdateData(name = it) },
        onUriChanged = { onUpdateData(image = it) },
        onTypeChanged = { onUpdateData(type = it) },
        onCostChanged = { onUpdateData(cost = it.toFloat()) },
        onPriceChanged = { onUpdateData(price = it.toFloat()) },
    )
}

@Composable
fun ItemInfoScreen(
    onAcceptButton: () -> Unit,
    onCancelButton: () -> Unit,
    uiState: ItemUiState,
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
            AcceptCancelButtons(
                uiState.isAcceptEnabled,
                onAcceptButton,
                onCancelButton
            )
        }
    )
}

@Composable
fun ItemsInfoContent(
    innerPadding: PaddingValues,
    uiState: ItemUiState,
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
        Row(
            modifier = Modifier.padding(start = pPadding, end = pPadding, top = pPadding),
        ) {
            Column(Modifier.weight(0.5F)) {
                Text(
                    text = "Product Info",
                    modifier = Modifier,
                    fontSize = 24.sp
                )
                IdNum(
                    idNum = uiState.iCode,
                    onTextChanged = { onCodeChanged(it) },
                )
            }
            ItemInfoImage(
                singlePhotoPickerLauncher = singlePhotoPickerLauncher,
                cImage = uiState.iImage,
                defaultImage = painterResource(id = R.drawable.no_photo_24)
            )
        }
        ItemName(
            label = "Item name",
            cFiscalName = uiState.iName,
            onTextChanged = { onNameChanged(it) }
        )
        ItemName(
            label = "Item type",
            cFiscalName = uiState.iType,
            onTextChanged = { onTypeChanged(it) }
        )

        Row (modifier = Modifier.padding(start = pPadding, end = pPadding)) {
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

    var colorFilter: ColorFilter? = null
    var image = uriToPainterImage(cImage)
    if (image == null) {
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        image = defaultImage
    }

    Box(
        modifier = Modifier
            .size(100.dp) // Size of the Box (background)
            .border(
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
                shape = RoundedCornerShape(5.dp)
            ), contentAlignment = Alignment.Center // Center content in the Box
    ) {
        Image(
            painter = image,
            contentDescription = "customer Image",
            modifier = Modifier
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                .height(90.dp)
                .width(90.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            colorFilter = colorFilter
        )
    }
}

@Composable
fun IdNum(idNum: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 16.dp),
        value = idNum,
        onValueChange = { onTextChanged(it) },
        label = { Text("Product code") }
    )
}

@Composable
fun ItemName(label: String, cFiscalName: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = pPadding, end = pPadding),
        value = cFiscalName,
        onValueChange = { onTextChanged(it) },
        label = { Text(label) }
    )
}

@Composable
fun ItemCost(label: String, amount: Float, onAmountChanged: (Float) -> Unit) {
    OutlinedTextField(
        value = amount.toString(),
        onValueChange = { onAmountChanged(it.toFloat()) },
        label = { Text(label) }
    )
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCustomerInfoScreen() {
    MyInvoiceTheme {
        ItemInfoScreen(
            onAcceptButton = {},
            onCancelButton = {},
            uiState = ItemUiState(),
            onNameChanged = {},
            onCodeChanged = {},
            onUriChanged = {},
            onTypeChanged = {},
            onCostChanged = {},
            onPriceChanged = {}
        )
    }
}
