package com.endcodev.myinvoice.ui.compose.screens.customers

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.CustomerUiState
import com.endcodev.myinvoice.ui.compose.components.BottomButtons
import com.endcodev.myinvoice.ui.compose.screens.invoice.ProgressBar
import com.endcodev.myinvoice.ui.viewmodels.CustomerInfoViewModel

val pPadding = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerInfoScreen(
    viewModel: CustomerInfoViewModel = hiltViewModel(),
    onAcceptButton: () -> Unit,
    onCancelButton: () -> Unit,
    customerIdentifier: String?
) {

    val uiState by viewModel.uiState.collectAsState()

    if (customerIdentifier != null){
        //only called when customerIdentifier changes,
        LaunchedEffect(customerIdentifier) {
            viewModel.getCustomer(customerIdentifier)
        }
    }

    if (uiState.isLoading)
        ProgressBar()
    else
    Scaffold(
        topBar = { },
        content = { innerPadding -> CustomerInfoContent(innerPadding, uiState, viewModel) },
        bottomBar = {
            BottomButtons(
                uiState.isAcceptEnabled,
                onAcceptClick = {
                    viewModel.saveCustomer() //todo return if correct or not, if correct navigate back
                },
                onCancelButton
            )
        }
    )
}

@Composable
fun CustomerInfoContent(
    innerPadding: PaddingValues,
    uiState: CustomerUiState,
    viewModel: CustomerInfoViewModel
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

                viewModel.updateUri(uri)
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
                    text = "Customer Info",
                    modifier = Modifier,
                    fontSize = 24.sp
                )
                CompanyIdNum(
                    uiState.cIdentifier,
                    onTextChanged = {
                        viewModel.onDataChanged(
                            identifier = it,
                            fiscalName = uiState.cFiscalName,
                            telephone = uiState.cTelephone
                        )
                    },
                )
            }
            CustomerInfoImage(singlePhotoPickerLauncher, uiState)

        }

        CompanyName(
            uiState.cFiscalName,
            onTextChanged = {
                viewModel.onDataChanged(
                    identifier = uiState.cIdentifier,
                    fiscalName = it,
                    telephone = uiState.cTelephone
                )
            }
        )

        CompanyEmail()
    }
}

@Composable
fun CustomerInfoImage(
    singlePhotoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    uiState: CustomerUiState
) {
    Image(
        painter = uriToPainterImage(
            uiState.cImage,
            painterResource(id = R.drawable.image_search_24)
        ),
        contentDescription = "Image",
        modifier = Modifier
            .clickable {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
            .height(100.dp)
            .width(100.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter
    )
}


@Composable
fun uriToPainterImage(uri: Uri?, default: Painter): Painter {
    if (uri == null)
        return default
    return rememberAsyncImagePainter(model = uri)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyIdNum(idNum: String, onTextChanged: (String) -> Unit) {

    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 16.dp),
        value = idNum,
        onValueChange = { onTextChanged(it) },
        label = { Text("Company ID") }
    )
}

@Composable
fun InfoTitle(text: String, modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        color = Color.Black,
        fontSize = 24.sp,
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
            .padding(start = pPadding, end = pPadding),
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
            .padding(start = pPadding, end = pPadding),
        value = text,
        onValueChange = { text = it },
        label = { Text("Email") }
    )
}


@Preview
@Composable
fun PreviewDetailsCustomer() {
    //CustomerInfoScreen(onAcceptClick = {}, onCancelClick = {})
}