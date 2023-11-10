package com.endcodev.myinvoice.ui.compose.screens.auth.forgot

import android.content.res.Configuration
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.compose.screens.auth.login.ImageLogo
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginButton
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginEnterEmail
import com.endcodev.myinvoice.ui.compose.screens.auth.login.SignUpLink
import com.endcodev.myinvoice.ui.compose.screens.auth.signup.SignUpTopBar
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun ForgotPassScreen(
    email: String,
    isForgotEnabled: Boolean,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onMailChanged: (String) -> Unit
) {
    Scaffold(
        topBar = {
            SignUpTopBar {
                onBackClick()
            }
        },
        content = { innerPadding ->
            ForgotBody(innerPadding, onLoginClick, email, isForgotEnabled, onMailChanged)
        }, bottomBar = { ForgotFooter(onBackClick) }
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
        LoginButton(stringResource(R.string.login_change_pass), isForgotEnabled, onLoginClick)
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
        SignUpLink(
            stringResource(R.string.login_remember_pass),
            stringResource(id = R.string.login_log_in),
            onBackClick
        )
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