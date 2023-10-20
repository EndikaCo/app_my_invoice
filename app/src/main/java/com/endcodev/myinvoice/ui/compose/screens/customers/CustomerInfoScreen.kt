package com.endcodev.myinvoice.ui.compose.screens.customers

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.CustomerUiState
import com.endcodev.myinvoice.ui.compose.components.BottomButtons
import com.endcodev.myinvoice.ui.viewmodels.CustomerInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerInfoScreen(
    viewModel: CustomerInfoViewModel = hiltViewModel(),
    onAcceptButton: () -> Unit,
    onCancelButton: () -> Unit,
    customerIdentifier: String?
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.getCustomerByName(customerIdentifier)

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
    Column(
        Modifier
            .padding(innerPadding)
            .fillMaxWidth()
    )
    {
        ConstraintLayout(
            modifier = Modifier
        ) {
            val (title, nif, image) = createRefs()

            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri ->
                    viewModel.updateUri(uri)
                }
            )

            InfoTitle(
                text = "Customer Info",
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(image.start)
                    width = Dimension.preferredWrapContent
                }
            )

            CompanyIdNum(uiState.cIdentifier, onTextChanged = {
                viewModel.onDataChanged(
                    identifier = it,
                    fiscalName = uiState.cFiscalName,
                    telephone = uiState.cTelephone
                )
            },
                modifier = Modifier.constrainAs(nif) {
                    top.linkTo(title.bottom, margin = 4.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(title.end)
                    width = Dimension.preferredWrapContent
                }
            )

            Image(
                painter = uriToPainterImage(uiState.cImage),
                contentDescription = "Image",
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(nif.end, margin = 20.dp)
                        end.linkTo(parent.end, margin = 20.dp)
                        bottom.linkTo(nif.bottom)
                    }.clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                }.height(100.dp)
                    .width(100.dp)
            )
        }

        CompanyName(uiState.cFiscalName, onTextChanged = {
            viewModel.onDataChanged(
                identifier = uiState.cIdentifier,
                fiscalName = it,
                telephone = uiState.cTelephone
            )
        })
        CompanyEmail()
    }
}


@Composable
fun uriToPainterImage(uri: Uri?): Painter {
    if (uri == null)
        return painterResource(id = R.drawable.image_search_24)
    return rememberAsyncImagePainter(model = uri)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyIdNum(idNum: String, onTextChanged: (String) -> Unit, modifier: Modifier) {

    OutlinedTextField(modifier = modifier.fillMaxWidth(),
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
        fontSize = 26.sp,
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
            .padding(start = 20.dp, end = 20.dp),
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
            .padding(start = 20.dp, end = 20.dp),
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