package com.endcodev.myinvoice.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.myinvoice.ui.screens.auth.LoginScreen
import com.endcodev.myinvoice.ui.screens.auth.ForgotPassScreen
import com.endcodev.myinvoice.ui.screens.auth.SignUpScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        // LOG-IN SCREEN
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.popBackStack() // clear nav history
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            )
        }
        //SIGN-UP SCREEN
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(
                onSignUpClick = {
                    navController.navigate(AuthScreen.Login.route)
            })
        }
        //FORGOT PASSWORD SCREEN
        composable(route = AuthScreen.Forgot.route) {
            ForgotPassScreen(onBackClick = {
                navController.navigate(AuthScreen.Login.route)
            })
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
}