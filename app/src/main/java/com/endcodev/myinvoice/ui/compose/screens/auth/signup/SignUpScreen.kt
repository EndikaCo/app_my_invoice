package com.endcodev.myinvoice.ui.compose.screens.auth.signup

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.compose.screens.auth.login.ImageLogo
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginButton
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginEnterEmail
import com.endcodev.myinvoice.ui.compose.screens.auth.login.LoginEnterPassWord
import com.endcodev.myinvoice.ui.navigation.AuthScreen
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme
import com.endcodev.myinvoice.ui.viewmodels.SignUpViewModel

@Composable
fun SignUpActions(navController: NavHostController) {
    val viewModel: SignUpViewModel = hiltViewModel()

    val email by viewModel.email.observeAsState(initial = "")
    val password by viewModel.password.observeAsState(initial = "")
    val repeatPassword by viewModel.repeatPassword.observeAsState(initial = "")
    val isSignUpEnabled by viewModel.isSignUpEnabled.observeAsState(initial = false)
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val success by viewModel.isSuccess.observeAsState(initial = false)

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.errors.collect { error ->
            Toast.makeText(context, error.asString(context), Toast.LENGTH_LONG).show()
        }
    }

    SignUpScreen(
        onSignUpClick = {
            navController.navigate(AuthScreen.Login.route)
            viewModel.createAccount()
        },
        success,
        isLoading,
        email,
        password,
        repeatPassword,
        isSignUpEnabled,
        onEmailChanged = {
            viewModel.onSignUpChanged(email = it, password = password, repeat = repeatPassword)
        },
        onPassChanged = {
            viewModel.onSignUpChanged(password = it, email = email, repeat = repeatPassword)

        },
        onVerifyChanged = {
            viewModel.onSignUpChanged(password = it, email = email, repeat = repeatPassword)
        })
}

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
fun SignUpTopBar(onSignUpClick: () -> Unit) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text("Sign Up")
        },
        navigationIcon = {
            IconButton(
                onClick = { onSignUpClick() }
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
        horizontalAlignment = Alignment.CenterHorizontally,
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
            ImageLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.size(16.dp))
            LoginEnterEmail(email) {
                onEmailChanged(it)
            }
            Spacer(modifier = Modifier.size(4.dp))
            LoginEnterPassWord(password) {
                onPassChanged(it)
            }
            Spacer(modifier = Modifier.size(4.dp))
            RepeatPassWord(repeatPassword) {

            }
            Spacer(modifier = Modifier.size(16.dp))
            LoginButton(stringResource(R.string.signup_sign_up_bt),isSignUpEnabled, onSignUpClick)
            Spacer(modifier = Modifier.size(16.dp))
            SignUpDivider(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.size(16.dp))
            SocialSignUp()
        }
}

@Composable
fun RepeatPassWord(password: String, onTextChanged: (String) -> Unit) {

    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Repeat password") },
        maxLines = 1,
        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color(0xFF222020),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                if (passwordVisibility) {
                    Icon(painterResource(id = R.drawable.visibility_24), contentDescription = "")
                } else {
                    Icon(
                        painterResource(id = R.drawable.visibility_off_24),
                        contentDescription = ""
                    )
                }
            }
        }, visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun SignUpDivider(modifier: Modifier) {
    Text(text = "OR", modifier = modifier, fontSize = 14.sp, color = Color(R.color.grey))
}

@Composable
fun SocialSignUp() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_facebook),
            contentDescription = "Social Sign in",
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "Continue with Facebook",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color(R.color.black),
            textDecoration = TextDecoration.Underline

        )
    }
}

@Composable
fun ToLogIn(onAlreadyLoggedClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onAlreadyLoggedClick()
            }, horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Already have an account?",
            fontSize = 15.sp,
            color = Color(0xFF8B8C8F)
        )
        Text(
            text = "Log in",
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color(R.color.black),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun SignUpFooter(onAlreadyLoggedClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
        ToLogIn(onAlreadyLoggedClick)
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    MyInvoiceTheme {
        SignUpScreen(
            onSignUpClick = {},
            false,
            false,
            "Email",
            "Password",
            "Repeat password",
            true,
            onEmailChanged= {},
            onPassChanged= {},
            onVerifyChanged= {},
            )
    }
}