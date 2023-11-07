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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.CustomerInfoUiState
import com.endcodev.myinvoice.ui.compose.components.BottomButtons
import com.endcodev.myinvoice.ui.compose.components.CountrySelection
import com.endcodev.myinvoice.ui.compose.screens.invoice.ProgressBar
import com.endcodev.myinvoice.ui.utils.uriToPainterImage

val pPadding = 20.dp

/**
 * Composable function that represents the screen for displaying customer details.
 *
 * @param onAcceptButton Callback function to be called when the accept button is clicked.
 * @param onCancelButton Callback function to be called when the cancel button is clicked.
 * @param uiState The current state of the customer information UI.
 * @param onUriChanged Callback function to be called when the URI is changed.
 * @param onFiscalNameChange Callback function to be called when the fiscal name is changed.
 * @param onIdentifierChange Callback function to be called when the identifier is changed.
 * @param onCountryChange Callback function to be called when the country is changed.
 * @param onEmailChange Callback function to be called when the email is changed.
 */
@Composable
fun CustomerDetailsScreen(
    onAcceptButton: () -> Unit,
    onCancelButton: () -> Unit,
    uiState: CustomerInfoUiState,
    onUriChanged: (Uri) -> Unit,
    onFiscalNameChange: (String) -> Unit,
    onIdentifierChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
) {
    if (uiState.isLoading)
        ProgressBar()
    else
        Scaffold(
            topBar = { },
            content = { innerPadding ->
                CustomerInfoContent(
                    innerPadding,
                    onUriChanged,
                    uiState,
                    onFiscalNameChange,
                    onIdentifierChange,
                    onCountryChange,
                    onEmailChange
                )
            },
            bottomBar = {
                BottomButtons(
                    uiState.isAcceptEnabled,
                    onAcceptButton,
                    onCancelButton
                )
            }
        )
}

/**
 * Composable function that represents the content of the customer information screen.
 * @param innerPadding Padding values for the inner content.
 * @param onUriChanged Callback function to be called when the URI is changed.
 * @param uiState The current state of the customer information UI.
 * @param onFiscalNameChange Callback function to be called when the fiscal name is changed.
 * @param onIdentifierChange Callback function to be called when the identifier is changed.
 * @param onCountryChange Callback function to be called when the country is changed.
 * @param onEmailChange Callback function to be called when the email is changed.
 */
@Composable
fun CustomerInfoContent(
    innerPadding: PaddingValues,
    onUriChanged: (Uri) -> Unit,
    uiState: CustomerInfoUiState,
    onFiscalNameChange: (String) -> Unit,
    onIdentifierChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
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
                    text = "Customer Info",
                    modifier = Modifier,
                    fontSize = 24.sp
                )
                CompanyIdNum(
                    idNum = uiState.cIdentifier,
                    onTextChanged = { onIdentifierChange(it) },
                )
            }
            CustomerInfoImage(
                singlePhotoPickerLauncher = singlePhotoPickerLauncher,
                cImage = uiState.cImage
            )
        }
        CompanyName(
            cFiscalName = uiState.cFiscalName,
            onTextChanged = { onFiscalNameChange(it) }
        )
        CompanyEmail(cEmail = uiState.cEmail, onEmailChanged = onEmailChange)
        CountrySelection(
            modifier = Modifier.padding(start = pPadding, end = pPadding, top = 3.dp),
            onSelection = onCountryChange
        )
    }
}


/**
 * Displays a customer's image or new image from photo picker launcher.
 * @param singlePhotoPickerLauncher The launcher used to pick a visual media (image) from the device.
 * @param cImage The current image URI of the customer.
 */
@Composable
fun CustomerInfoImage(
    singlePhotoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    cImage: Uri?) {
    Image(
        painter = uriToPainterImage(
            cImage,
            painterResource(id = R.drawable.person_24)
        ),
        contentDescription = "customer Image",
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

@Composable
fun CompanyEmail(cEmail: String, onEmailChanged: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = pPadding, end = pPadding),
        value = cEmail,
        onValueChange = { onEmailChanged(it) },
        label = { Text("Email") }
    )
}


@Preview
@Composable
fun PreviewCustomerInfoScreen() {
    CustomerDetailsScreen(
        onAcceptButton = {},
        onCancelButton = {},
        CustomerInfoUiState(),
        onUriChanged = {},
        onFiscalNameChange = {},
        onIdentifierChange = {},
        onCountryChange = {},
        onEmailChange = {}
    )
}