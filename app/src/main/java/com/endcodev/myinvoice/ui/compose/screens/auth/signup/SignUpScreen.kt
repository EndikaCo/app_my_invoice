package com.endcodev.myinvoice.ui.compose.screens.auth.signup

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.compose.screens.auth.login.ImageLogo
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginButton
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginEnterEmail
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginEnterPassWord
import com.endcodev.myinvoice.ui.compose.screens.auth.login.OrDivider
import com.endcodev.myinvoice.ui.compose.screens.auth.login.SignUpLink
import com.endcodev.myinvoice.ui.compose.screens.auth.login.SocialLogin
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit,
    success: Boolean,
    isLoading: Boolean,
    email: String,
    password: String,
    repeatPassword: String,
    isSignUpEnabled: Boolean,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onVerifyChanged: (String) -> Unit,
    ) {

    if (success) {
        onSignUpClick()
        Log.v("***", "SUCCESS")
    }

    Scaffold(
        topBar = { SignUpTopBar(onSignUpClick) },
        content = { innerPadding ->
            SignUpBody(
                innerPadding,
                isLoading,
                email,
                password,
                repeatPassword,
                isSignUpEnabled,
                onEmailChanged,
                onPassChanged,
                onVerifyChanged,
                onSignUpClick
            )
        },
        bottomBar = { SignUpFooter(onSignUpClick) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpTopBar(onClick: () -> Unit) {
    androidx.compose.material3.TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Go back")
            }
        },
    )
}

@Composable
fun VerificationProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(text = "Waiting for verification...")
    }
}

@Composable
fun SignUpBody(
    innerPadding: PaddingValues,
    isLoading: Boolean,
    email: String,
    password: String,
    repeatPassword: String,
    isSignUpEnabled: Boolean,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onVerifyChanged: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    if (isLoading)
        Column {
            VerificationProgressBar()
        }
    else
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxHeight()
        ) {
            ImageLogo(Modifier.align(CenterHorizontally))
            Spacer(modifier = Modifier.size(16.dp))
            LoginEnterEmail(email) {
                onEmailChanged(it)
            }
            Spacer(modifier = Modifier.size(4.dp))
            LoginEnterPassWord(stringResource(R.string.login_password), password) {
                onPassChanged(it)
            }
            Spacer(modifier = Modifier.size(4.dp))
            LoginEnterPassWord(stringResource(R.string.login__repeat_password), repeatPassword) {
                onVerifyChanged(it)
            }
            Spacer(modifier = Modifier.size(16.dp))
            LoginButton(stringResource(R.string.login__sign_up_bt), isSignUpEnabled, onSignUpClick)
            Spacer(modifier = Modifier.size(16.dp))
            OrDivider(Modifier.align(CenterHorizontally))
            Spacer(modifier = Modifier.size(16.dp))
            SocialLogin(Modifier.align(CenterHorizontally))
        }
}

@Composable
fun SignUpFooter(onTextLinkClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
        SignUpLink(
            text1 = stringResource(R.string.login_already_have_account),
            text2 = stringResource(R.string.login_log_in),
            onClick = onTextLinkClick
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    MyInvoiceTheme {

        val success = false
        val isLoading = false

        SignUpScreen(
            onSignUpClick = {},
            success,
            isLoading,
            "Email",
            "Password",
            "Repeat password",
            true,
            onEmailChanged = {},
            onPassChanged = {},
            onVerifyChanged = {},
        )
    }
}