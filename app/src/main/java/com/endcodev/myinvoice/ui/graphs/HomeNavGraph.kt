package com.endcodev.myinvoice.ui.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.myinvoice.ui.screens.home.CustomersScreen
import com.endcodev.myinvoice.ui.screens.ScreenContent

sealed class Routes(val routes: String) {
    object HomeScreen : Routes("home")
    object CustomerScreen : Routes("customer")
    object SettingsScreen : Routes("settings")
}

//BOTTOM BAR
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Routes.HomeScreen.routes
    ) {
        //HOME
        composable(route = Routes.HomeScreen.routes) {
            ScreenContent(
                name = Routes.HomeScreen.routes,
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        //CUSTOMERS
        composable(route = Routes.CustomerScreen.routes) {
            CustomersScreen(
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        //SETTINGS
        composable(route = Routes.SettingsScreen.routes) {
            ScreenContent(
                name = Routes.SettingsScreen.routes,
                onClick = { }
            )
        }
        detailsNavGraph(navController = navController)
    }
}


fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        //INFORMATION SCREEN
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        //OVERVIEW SCREEN
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}