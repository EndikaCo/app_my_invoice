package com.endcodev.myinvoice.presentation.compose.screens.details

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.endcodev.myinvoice.domain.models.customer.CustomerUiState
import com.endcodev.myinvoice.domain.utils.App
import com.endcodev.myinvoice.presentation.compose.components.CountrySelection
import com.endcodev.myinvoice.presentation.compose.components.MyBottomBar
import com.endcodev.myinvoice.presentation.compose.components.filteredImage
import com.endcodev.myinvoice.presentation.compose.screens.home.content.ProgressBar
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme
import com.endcodev.myinvoice.presentation.navigation.Routes
import com.endcodev.myinvoice.presentation.viewmodels.CustomerInfoViewModel

/**
 * Handles actions related to customer details.
 * @param customerIdentifier The identifier of the customer.
 * @param navController The navigation controller used for navigation.
 */
@Composable
fun CustomerDetailActions(
    customerIdentifier: String?,
    navController: NavHostController
) {

    val viewModel: CustomerInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (customerIdentifier != null) {
        LaunchedEffect(customerIdentifier) {
            viewModel.getCustomer(customerIdentifier)
        }
    }

    fun onUpdateData(
        identifier: String? = null,
        fiscalName: String? = null,
        telephone: String? = null,
        country: String? = null,
        email: String? = null
    ) {
        viewModel.onDataChanged(
            identifier = identifier ?: uiState.cIdentifier,
            fiscalName = fiscalName ?: uiState.cFiscalName,
            telephone = telephone ?: uiState.cTelephone,
            country = country ?: uiState.cCountry,
            email = email ?: uiState.cEmail
        )
    }

    CustomerDetailScreen(
        onSaveClick = {
            viewModel.saveCustomer()
            navController.navigate(Routes.CustomerContent.routes)
        },
        uiState,
        onUriChanged = { viewModel.updateUri(it) },
        onFiscalNameChange = { onUpdateData(fiscalName = it) },
        onIdentifierChange = { onUpdateData(identifier = it) },
        onCountryChange = { onUpdateData(country = it) },
        onEmailChange = { onUpdateData(email = it) },
        onDeleteClick = { viewModel.deleteCustomer() }
    )
}

val pPadding = 20.dp

/**
 * Composable function that represents the screen for displaying customer details.
 *
 * @param onSaveClick Callback function to be called when the accept button is clicked.
 * @param uiState The current state of the customer information UI.
 * @param onUriChanged Callback function to be called when the URI is changed.
 * @param onFiscalNameChange Callback function to be called when the fiscal name is changed.
 * @param onIdentifierChange Callback function to be called when the identifier is changed.
 * @param onCountryChange Callback function to be called when the country is changed.
 * @param onEmailChange Callback function to be called when the email is changed.
 */
@Composable
fun CustomerDetailScreen(
    onSaveClick: () -> Unit,
    uiState: CustomerUiState,
    onUriChanged: (Uri?) -> Unit,
    onFiscalNameChange: (String) -> Unit,
    onIdentifierChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDeleteClick: () -> Unit
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
                MyBottomBar(
                    enableDelete = false,
                    enableSave = uiState.isSaveEnabled,
                    addItemVisible = false,
                    onDeleteClick = onDeleteClick,
                    onAddItemClick = {},
                    onAcceptClick = onSaveClick
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
    onUriChanged: (Uri?) -> Unit,
    uiState: CustomerUiState,
    onFiscalNameChange: (String) -> Unit,
    onIdentifierChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
) {
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
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                CompanyIdNum(
                    idNum = uiState.cIdentifier,
                    onTextChanged = { onIdentifierChange(it) },
                )
            }
            CustomerInfoImage(
                onUriChanged,
                cImage = uiState.cImage,
                defaultImage = painterResource(id = R.drawable.person_24)
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

@Composable
fun CustomerInfoImage(
    onUriChanged: (Uri?) -> Unit,
    cImage: Uri?,
    defaultImage: Painter
) {
    val image = filteredImage(cImage, defaultImage)
    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) {
                Log.e(App.tag, "Image not valid")
                onUriChanged(null)
            } else {
                // Grant read permission to the obtained URI
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)
                onUriChanged(uri)
            }
        }
    )

    Box(
        modifier = Modifier
            .size(100.dp) // Size of the Box (background)
            .border(
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
                shape = CircleShape
            ), contentAlignment = Alignment.Center // Center content in the Box
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
                .height(90.dp)
                .width(90.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            colorFilter = image.filter
        )
    }
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


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCustomerDetailScreen() {
    MyInvoiceTheme {
        CustomerDetailScreen(
            onSaveClick = {},
            CustomerUiState(),
            onUriChanged = {},
            onFiscalNameChange = {},
            onIdentifierChange = {},
            onCountryChange = {},
            onEmailChange = {},
            onDeleteClick = {}
        )
    }
}
