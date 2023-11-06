package com.endcodev.myinvoice.ui.compose.screens.auth

import android.app.Activity
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.navigation.AuthScreen
import com.endcodev.myinvoice.ui.navigation.Graph
import com.endcodev.myinvoice.ui.viewmodels.LoginViewModel

@Composable
fun LoginActions(navController: NavHostController) {

    val viewModel: LoginViewModel = hiltViewModel()

    val email by viewModel.email.observeAsState(initial = "")
    val password by viewModel.password.observeAsState(initial = "")
    val isLoginEnabled by viewModel.isLoginEnabled.observeAsState(initial = false)

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.errors.collect { error ->
            error.asString(context)
            Toast.makeText(context, error.asString(context), Toast.LENGTH_LONG).show()
        }
    }

    LoginScreen(
        email = email,
        password = password,
        isLoginEnables = isLoginEnabled,
        onLoginClick = {
            viewModel.login()
            navController.popBackStack() // clear nav history
            navController.navigate(Graph.HOME)
        },
        onSignUpClick = {
            navController.navigate(AuthScreen.SignUp.route)
        },
        onForgotClick = {
            navController.navigate(AuthScreen.Forgot.route)
        },
        onEmailChanged = {
            viewModel.onLoginChanged(it, password)
        },
        onPassChanged = {
            viewModel.onLoginChanged(password = it, email = email)
        }
    )
}

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
) {
    Scaffold(
        topBar = { LoginHeader() },
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

@Composable
fun LoginHeader() {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = stringResource(R.string.login_close_app),
        modifier = Modifier.clickable { activity.finish() })
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
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(16.dp))
        Email(email) { onLoginChanged(it) }
        Spacer(modifier = Modifier.size(4.dp))
        LoginPassWord(password) { onPassChanged(it) }
        Spacer(modifier = Modifier.size(16.dp))
        ForgotPassword(Modifier.align(Alignment.End), onForgotClick)
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(loginEnabled = isLoginEnabled, onLoginClick = onLoginClick)
        Spacer(modifier = Modifier.size(16.dp))
        LoginDivider(Modifier.align(Alignment.CenterHorizontally))
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
        SignUpLink(onSignUpClick)
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.title_logo),
        contentDescription = stringResource(R.string.login_logo),
        modifier = modifier
            .height(100.dp)
            .width(300.dp)
    )
}

@Composable
fun Email(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.login_email)) },
        maxLines = 1,
        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color(0xFF222020),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LoginPassWord(password: String, onTextChanged: (String) -> Unit) {

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.login_password)) },
        maxLines = 1,
        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color(0xFF222020),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
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
        }, visualTransformation = if (passwordVisibility)
            VisualTransformation.None
        else
            PasswordVisualTransformation()
    )
}


@Composable
fun ForgotPassword(modifier: Modifier, onForgotClick: () -> Unit) {
    Text(
        text = stringResource(R.string.login_forgot_password),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(R.color.black),
        modifier = modifier.clickable { onForgotClick() },
        textDecoration = TextDecoration.Underline,
    )
}

@Composable
fun LoginButton(loginEnabled: Boolean, onLoginClick: () -> Unit) {
    Button(
        onClick = { onLoginClick() },
        enabled = loginEnabled,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(R.color.light_black),
            disabledContainerColor = Color(0x80646464),
            disabledContentColor = Color.White,
            contentColor = Color.White,
        )
    ) {
        Text(text = "Log In", fontSize = 20.sp)
    }
}


@Composable
fun LoginDivider(modifier: Modifier) {
    Text(text = "OR", modifier = modifier, fontSize = 14.sp, color = Color(R.color.grey))
}

@Composable
fun SocialLogin(modifier : Modifier) {
    Row(
        modifier = modifier
            .height(36.dp)
            .background(Color.LightGray, RoundedCornerShape(16.dp))
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = com.google.firebase.database.ktx.R.drawable.googleg_standard_color_18),
            contentDescription = "Social login image",
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "Continue with Google",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color(R.color.black),
        )
    }
}

@Composable
fun SignUpLink(onSignUpClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onSignUpClick() }, horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.login_don_t_have_an_account),
            fontSize = 15.sp,
            color = Color(R.color.light_black)
        )
        Text(
            text = stringResource(R.string.login_sign_up_link),
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color(R.color.black),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    }
}