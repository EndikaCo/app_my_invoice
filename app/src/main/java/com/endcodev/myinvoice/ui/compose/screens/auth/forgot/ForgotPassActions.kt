package com.endcodev.myinvoice.ui.compose.screens.auth.forgot

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.ui.navigation.AuthScreen
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