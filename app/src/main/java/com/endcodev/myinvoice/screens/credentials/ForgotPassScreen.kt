package com.endcodev.myinvoice.screens.credentials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.viewmodels.ForgotViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPassScreen(
    onBackClick: () ->Unit,
    viewModel: ForgotViewModel = viewModel()
) {
    Scaffold(
        topBar = { ForgotHeader(onBackClick) },
        content = { innerPadding ->
            ForgotBody(innerPadding, viewModel = viewModel,  onBackClick) },
    )
}

@Composable
fun ForgotBody(innerPadding: PaddingValues, viewModel: ForgotViewModel, onLoginClick: () ->Unit) {
    val email by viewModel.email.observeAsState(initial = "")
    val isForgotEnabled by viewModel.isForgotEnabled.observeAsState(initial = false)


    Column (Modifier.padding(innerPadding).padding(start = 16.dp, end = 16.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.Center){
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(16.dp))
        Email(email) { viewModel.onLoginChanged(email = it) }
        Spacer(modifier = Modifier.size(4.dp))
        LoginButton(
            loginEnabled = isForgotEnabled,
            onLoginClick = onLoginClick,
            viewModel = viewModel
        )
    }
}

@Composable
fun ForgotHeader(onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
        SignUp(onBackClick)
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyInvoiceTheme {
        //ForgotPassScreen()
    }
}