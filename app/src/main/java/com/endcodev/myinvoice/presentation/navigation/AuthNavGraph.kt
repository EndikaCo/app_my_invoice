package com.endcodev.myinvoice.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.myinvoice.presentation.compose.screens.auth.ForgotPassActions
import com.endcodev.myinvoice.presentation.compose.screens.auth.LoginActions
import com.endcodev.myinvoice.presentation.compose.screens.auth.SignUpActions


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        // LOG-IN SCREEN
        composable(route = AuthScreen.Login.route) {
            LoginActions(navController = navController)
        }
        //SIGN-UP SCREEN
        composable(route = AuthScreen.SignUp.route) {
            SignUpActions(navController = navController)
        }
        //FORGOT PASSWORD SCREEN
        composable(route = AuthScreen.Forgot.route) {
            ForgotPassActions(navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
}