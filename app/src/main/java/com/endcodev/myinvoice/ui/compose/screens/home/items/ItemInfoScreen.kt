package com.endcodev.myinvoice.ui.compose.screens.home.items

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.endcodev.myinvoice.ui.compose.screens.home.customers.details.CustomerInfoImage
import com.endcodev.myinvoice.ui.compose.screens.home.customers.details.pPadding
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
        LaunchedEffect(itemId) {
            viewModel.getItem(itemId)
        }
    }

    fun onUpdateData(
        code: String? = null,
        name: String? = null,
        image: Uri? = null
    ) {
        viewModel.onDataChanged(
            code = code ?: uiState.iCode,
            name = name ?: uiState.iName,
            image = image ?: uiState.iImage
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
        onUriChanged = {onUpdateData(image = it)}
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
) {
    Scaffold(
        topBar = { },
        content = { innerPadding ->
            ItemsInfoContent(
                innerPadding,
                uiState, onCodeChanged, onNameChanged, onUriChanged
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
    onUriChanged: (Uri) -> Unit
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
            CustomerInfoImage(
                singlePhotoPickerLauncher = singlePhotoPickerLauncher,
                cImage = uiState.iImage,
                defaultImage = painterResource(id = R.drawable.item_add_24)
            )
        }
        ItemName(
            cFiscalName = uiState.iName,
            onTextChanged = { onNameChanged(it) }
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
fun ItemName(cFiscalName: String, onTextChanged: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = pPadding, end = pPadding),
        value = cFiscalName,
        onValueChange = { onTextChanged(it) },
        label = { Text("Company name") }
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
            onUriChanged = {}
        )
    }
}
