package com.endcodev.myinvoice.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.endcodev.myinvoice.ui.compose.screens.customers.CustomerInfoScreen
import com.endcodev.myinvoice.ui.compose.screens.customers.CustomerInfoScreenActions
import com.endcodev.myinvoice.ui.compose.screens.customers.CustomersListContentActions
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
            CustomersListContentActions(navController)
        }
        //ITEMS
        composable(route = Routes.ItemsContent.routes) {
            ItemsContent(onButtonClick = { navController.navigate(DetailsScreen.Item.route) })
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

        // CUSTOMER SCREEN
        composable(
            route = "${DetailsScreen.Customer.route}/{cIdentifier}",
            arguments = listOf(navArgument("cIdentifier") { type = NavType.StringType })
        ) { backStackEntry ->
            val customerIdentifier = backStackEntry.arguments?.getString("cIdentifier")
            CustomerInfoScreenActions(customerIdentifier = customerIdentifier, navController)
        }
        // CUSTOMER SCREEN (Without Arguments)
        composable(
            route = DetailsScreen.Customer.route,
        ) {
            CustomerInfoScreenActions(customerIdentifier = null, navController)
        }

        //INVOICE SCREEN
        composable(route = DetailsScreen.Invoice.route) {
            InvoiceInfoScreen(onNavButtonClick = {})
        }
        //ITEMS SCREEN
        composable(route = DetailsScreen.Item.route) {
            ItemInfoScreen(
                onAcceptButton = { navController.navigate(Routes.ItemsContent.routes) },
                onCancelButton = { navController.navigate(Routes.ItemsContent.routes) })
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


