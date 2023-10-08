package com.endcodev.myinvoice.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.myinvoice.ui.screens.customers.CustomersContent
import com.endcodev.myinvoice.ui.screens.invoice.InvoicesContent
import com.endcodev.myinvoice.ui.screens.items.ItemsContent

sealed class Routes(val routes: String) {
    object InvoicesContent : Routes("invoices")
    object CustomerContent : Routes("customers")
    object ItemsContent : Routes("items")
}

//BOTTOM BAR
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Routes.InvoicesContent.routes
    ) {
        //HOME
        composable(route = Routes.InvoicesContent.routes) {
            InvoicesContent()
        }
        //CUSTOMERS
        composable(route = Routes.CustomerContent.routes) {
            CustomersContent()
        }
        //SETTINGS
        composable(route = Routes.ItemsContent.routes) {
            ItemsContent(
                name = Routes.ItemsContent.routes,
                onClick = { }
            )
        }
        detailsNavGraph(navController = navController)
    }
}


sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        //INFORMATION SCREEN
        composable(route = DetailsScreen.Information.route) {
            ItemsContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        //OVERVIEW SCREEN
        composable(route = DetailsScreen.Overview.route) {
            ItemsContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}


