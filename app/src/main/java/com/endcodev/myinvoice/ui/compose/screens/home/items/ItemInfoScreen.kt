package com.endcodev.myinvoice.ui.compose.screens.home.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.model.ItemUiState
import com.endcodev.myinvoice.ui.compose.components.AcceptCancelButtons
import com.endcodev.myinvoice.ui.compose.components.InfoImage
import com.endcodev.myinvoice.ui.compose.screens.home.customers.details.CompanyName
import com.endcodev.myinvoice.ui.compose.screens.home.customers.details.InfoTitle
import com.endcodev.myinvoice.ui.viewmodels.ItemInfoViewModel

@Composable
fun ItemInfoScreen(
    viewModel: ItemInfoViewModel = hiltViewModel(),
    onAcceptButton: () -> Unit,
    onCancelButton: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { },
        content = { innerPadding ->
            ItemsInfoContent(innerPadding, uiState, viewModel)
        },
        bottomBar = {
            AcceptCancelButtons(
                uiState.isAcceptEnabled,
                onAcceptClick = {},
                onCancelButton
            )
        }
    )
}

@Composable
fun ItemsInfoContent(
    innerPadding: PaddingValues,
    uiState: ItemUiState,
    viewModel: ItemInfoViewModel
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

            InfoTitle(
                text = "Item Info",
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(image.start)
                    width = Dimension.preferredWrapContent
                }
            )

            ItemIdNum(uiState.iCode, onTextChanged = {
                viewModel.onDataChanged(
                    code = it,
                    name = uiState.iName,
                )
            },
                modifier = Modifier.constrainAs(nif) {
                    top.linkTo(title.bottom, margin = 4.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(title.end)
                    width = Dimension.preferredWrapContent
                }
            )

            InfoImage(
                size = 110,
                image = painterResource(id = R.drawable.image_search_24),
                modifier = Modifier.constrainAs(image) {
                    start.linkTo(nif.end, margin = 40.dp)
                    end.linkTo(parent.end, margin = 40.dp)
                    bottom.linkTo(nif.bottom)
                })
        }

        CompanyName(uiState.iName, onTextChanged = {
            viewModel.onDataChanged(
            code = uiState.iCode,
            name = it,
        )})
    }



}

@Composable
fun ItemIdNum(idNum: String, onTextChanged: (String) -> Unit, modifier: Modifier) {

    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        value = idNum,
        onValueChange = { onTextChanged(it) },
        label = { Text("Company ID") }
    )
}

