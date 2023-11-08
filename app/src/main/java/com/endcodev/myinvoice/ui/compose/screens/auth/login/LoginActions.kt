package com.endcodev.myinvoice.ui.compose.screens.auth.login

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
    val activity = LocalContext.current as Activity
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
        },
        onExitClick = {activity.finish()}
    )
}
