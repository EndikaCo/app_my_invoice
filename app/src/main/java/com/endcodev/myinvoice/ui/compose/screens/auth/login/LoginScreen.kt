package com.endcodev.myinvoice.ui.compose.screens.auth.login

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun LoginScreen(
    email: String,
    password: String,
    isLoginEnables: Boolean,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onExitClick: () -> Unit
) {
    Scaffold(
        topBar = { LoginHeader(onExitClick) },
        content = { innerPadding ->
            LoginBody(
                innerPadding, email,
                password,
                isLoginEnables,
                onLoginClick,
                onForgotClick,
                onEmailChanged,
                onPassChanged
            )
        },
        bottomBar = { LoginFooter(onSignUpClick) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginHeader(onExitClick: () -> Unit) {
    androidx.compose.material3.TopAppBar(
        title = { Text(text = "EXIT",fontSize = 15.sp, modifier = Modifier.clickable { onExitClick() }) },
    )
}

@Composable
fun LoginBody(
    innerPadding: PaddingValues,
    email: String,
    password: String,
    isLoginEnabled: Boolean,
    onLoginClick: () -> Unit,
    onForgotClick: () -> Unit,
    onLoginChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        ImageLogo(Modifier.align(CenterHorizontally))
        Spacer(modifier = Modifier.size(16.dp))
        LoginEnterEmail(email) { onLoginChanged(it) }
        Spacer(modifier = Modifier.size(4.dp))
        LoginEnterPassWord(stringResource(R.string.login_password), password) { onPassChanged(it) }
        Spacer(modifier = Modifier.size(16.dp))
        ForgotPassword(Modifier.align(Alignment.End), onForgotClick)
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(stringResource(R.string.login_login_bt), isLoginEnabled, onLoginClick)
        Spacer(modifier = Modifier.size(16.dp))
        OrDivider(Modifier.align(CenterHorizontally))
        Spacer(modifier = Modifier.size(16.dp))
        SocialLogin(Modifier.align(CenterHorizontally))
    }
}

@Composable
fun LoginFooter(onSignUpClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier
                .background(Color(R.color.transparent))
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
        SignUpLink(
            stringResource(R.string.login_dont_have_account),
            stringResource(R.string.login_sign_up_link),
            onSignUpClick
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Text(text = stringResource(id = R.string.app_name_m), fontSize = 60.sp, modifier = modifier)
}

@Composable
fun LoginEnterEmail(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.login_email)) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    )
}

@Composable
fun LoginEnterPassWord(text: String, password: String, onTextChanged: (String) -> Unit) {

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = text) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                if (passwordVisibility)
                    Icon(painterResource(id = R.drawable.visibility_24), contentDescription = "")
                else
                    Icon(
                        painterResource(id = R.drawable.visibility_off_24),
                        contentDescription = stringResource(R.string.login_visibility_change_icon)
                    )
            }
        },
        visualTransformation = if (passwordVisibility)
            VisualTransformation.None
        else
            PasswordVisualTransformation()
    )
}

@Composable
fun ForgotPassword(modifier: Modifier, onForgotClick: () -> Unit) {
    Text(
        text = stringResource(R.string.login_forgot_password),
        fontSize = 15.sp,
        modifier = modifier.clickable { onForgotClick() },
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun LoginButton(text: String, loginEnabled: Boolean, onLoginClick: () -> Unit) {
    Button(
        onClick = { onLoginClick() },
        enabled = loginEnabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Transparent)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun OrDivider(modifier: Modifier) {
    Text(text = "OR", modifier = modifier, fontSize = 14.sp)
}

@Composable
fun SocialLogin(modifier: Modifier) {
    Row(
        modifier = modifier
            .height(36.dp)
            .border(
                BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.Transparent, RoundedCornerShape(16.dp))
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = com.google.firebase.database.ktx.R.drawable.googleg_standard_color_18),
            contentDescription = stringResource(R.string.login_social_login_image),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "Continue with Google",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}

@Composable
fun SignUpLink(text1: String, text2: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }, horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text1,
            fontSize = 15.sp,
        )
        Text(
            text = text2,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    MyInvoiceTheme {
        LoginScreen(
            email = "Email",
            password = "password",
            isLoginEnables = true,
            onLoginClick = {},
            onSignUpClick = {},
            onForgotClick = {},
            onEmailChanged = {},
            onPassChanged = {},
            onExitClick = {}
        )
    }
}