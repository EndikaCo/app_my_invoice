package com.endcodev.myinvoice.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.myinvoice.ui.compose.screens.customers.CustomerInfoScreen
import com.endcodev.myinvoice.ui.compose.screens.customers.CustomersContent
import com.endcodev.myinvoice.ui.compose.screens.invoice.InvoiceInfoScreen
import com.endcodev.myinvoice.ui.compose.screens.invoice.InvoicesContent
import com.endcodev.myinvoice.ui.compose.screens.items.ItemInfoScreen
import com.endcodev.myinvoice.ui.compose.screens.items.ItemsContent

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
            InvoicesContent(onButtonClick = { navController.navigate(DetailsScreen.Invoice.route) })
        }
        //CUSTOMERS
        composable(route = Routes.CustomerContent.routes) {
            CustomersContent(onButtonClick = { navController.navigate(DetailsScreen.Customer.route) })
        }
        //ITEMS
        composable(route = Routes.ItemsContent.routes) {
            ItemsContent(onButtonClick = { navController.navigate(DetailsScreen.Item.route)})
        }
        detailsNavGraph(navController = navController)
    }
}


sealed class DetailsScreen(val route: String) {
    object Customer : DetailsScreen(route = "CUSTOMER")
    object Invoice : DetailsScreen(route = "INVOICE")
    object Item : DetailsScreen(route = "ITEM")
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Customer.route
    ) {
        //CUSTOMER SCREEN
        composable(route = DetailsScreen.Customer.route) {
            CustomerInfoScreen(
                onAcceptClick = { navController.navigate(Routes.CustomerContent.routes) },
                onCancelClick = { navController.navigate(Routes.CustomerContent.routes) })
        }
        //INVOICE SCREEN
        composable(route = DetailsScreen.Invoice.route) {
            InvoiceInfoScreen()
        }
        //ITEMS SCREEN
        composable(route = DetailsScreen.Item.route) {
            ItemInfoScreen()
        }
        /*//ITEMS SCREEN
        composable(route = DetailsScreen.Item.route) {
            ItemsContent(name = DetailsScreen.Item.route) {
                navController.popBackStack(
                    route = DetailsScreen.Item.route,
                    inclusive = false
                )
            }
        }*/
    }
}


