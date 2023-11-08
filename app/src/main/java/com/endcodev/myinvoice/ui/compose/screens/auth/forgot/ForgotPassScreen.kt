package com.endcodev.myinvoice.ui.compose.screens.auth.forgot

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.compose.screens.auth.login.ImageLogo
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginButton
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginEnterEmail
import com.endcodev.myinvoice.ui.compose.screens.auth.login.SignUpLink
import com.endcodev.myinvoice.ui.navigation.AuthScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.ForgotViewModel

@Composable
fun ForgotPassActions(navController: NavHostController) {
    val viewModel: ForgotViewModel = hiltViewModel()

    val email by viewModel.email.observeAsState(initial = "")
    val isForgotEnabled by viewModel.isForgotEnabled.observeAsState(initial = false)

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel) {
        viewModel.errors.collect { error ->
            error.asString(context)
            Toast.makeText(context, error.asString(context), Toast.LENGTH_LONG).show()
        }
    }
    ForgotPassScreen(
        email,
        isForgotEnabled,
        onBackClick = { navController.navigate(AuthScreen.Login.route) },
        onLoginClick = { viewModel.forgotPass() },
        onMailChanged = { viewModel.onLoginChanged(email = it) }
    )
}

@Composable
fun ForgotPassScreen(
    email: String,
    isForgotEnabled: Boolean,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onMailChanged: (String) -> Unit
) {
    Scaffold(
        topBar = { ForgotHeader(onBackClick) },
        content = { innerPadding ->
            ForgotBody(innerPadding, onLoginClick, email, isForgotEnabled, onMailChanged)
        }, bottomBar = { ForgotFooter(onBackClick) }
    )
}

@Composable
fun ForgotHeader(onBackClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.KeyboardArrowLeft,
        contentDescription = stringResource(R.string.forgot_icon_back),
        modifier = Modifier
            .clickable { onBackClick() }
            .padding(start = 8.dp, top = 8.dp)
    )
}

@Composable
fun ForgotBody(
    innerPadding: PaddingValues,
    onLoginClick: () -> Unit,
    email: String,
    isForgotEnabled: Boolean,
    onMailChanged: (String) -> Unit,
) {
    Column(
        Modifier
            .padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(16.dp))
        LoginEnterEmail(email, onMailChanged)
        Spacer(modifier = Modifier.size(30.dp))
        LoginButton(stringResource(R.string.forgot_change_pass), isForgotEnabled, onLoginClick)
    }
}

@Composable
fun ForgotFooter(onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
        SignUpLink(onBackClick)
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForgotPassScreenPreview() {
    MyInvoiceTheme {
        ForgotPassScreen(
            "Email",
            true,
            onBackClick = {},
            onLoginClick = {},
            onMailChanged = {}
        )
    }
}