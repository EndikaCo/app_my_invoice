package com.endcodev.myinvoice.ui.compose.screens.auth.signup

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.ui.navigation.AuthScreen
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
        success = success,
        isLoading = isLoading,
        email = email,
        password = password,
        repeatPassword = repeatPassword,
        isSignUpEnabled = isSignUpEnabled,
        onSignUpClick = {
            navController.navigate(AuthScreen.Login.route)
            viewModel.createAccount()
        },
        onEmailChanged = {
            viewModel.onSignUpChanged(email = it, password = password, repeat = repeatPassword)
        },
        onPassChanged = {
            viewModel.onSignUpChanged(password = it, email = email, repeat = repeatPassword)
        },
        onVerifyChanged = {
            viewModel.onSignUpChanged(password = password, email = email, repeat = it)
        })
}