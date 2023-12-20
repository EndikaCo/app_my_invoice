package com.endcodev.myinvoice.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.endcodev.myinvoice.ui.compose.screens.home.content.HomeCustomersContentActions
import com.endcodev.myinvoice.ui.compose.screens.details.CustomerDetailActions
import com.endcodev.myinvoice.ui.compose.screens.details.InvoiceDetailActions
import com.endcodev.myinvoice.ui.compose.screens.home.content.HomeInvoicesContentActions
import com.endcodev.myinvoice.ui.compose.screens.details.ProductsDetailScreenActions
import com.endcodev.myinvoice.ui.compose.screens.home.content.HomeProductContentActions

sealed class Routes(val routes: String) {
    object InvoicesContent : Routes("invoices")
    object CustomerContent : Routes("customers")
    object ItemsContent : Routes("items")
}

//BOTTOM BAR
@Composable
fun HomeNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Routes.CustomerContent.routes
    ) {
        //HOME
        composable(route = Routes.InvoicesContent.routes) {
            HomeInvoicesContentActions(navController, paddingValues)
        }
        //CUSTOMERS
        composable(route = Routes.CustomerContent.routes) {
            HomeCustomersContentActions(navController, paddingValues)
        }
        //ITEMS
        composable(route = Routes.ItemsContent.routes) {
            HomeProductContentActions(navController, paddingValues)
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
            route = "${DetailsScreen.Invoice.route}/{iId}",
            arguments = listOf(navArgument("iId") { type = NavType.StringType })
        ) { backStackEntry ->
            val invoiceId = backStackEntry.arguments?.getString("iId")
            InvoiceDetailActions(invoiceId = invoiceId, navController)
        }
        //INVOICE SCREEN
        composable(route = DetailsScreen.Invoice.route) {
            InvoiceDetailActions(invoiceId = null, navController)
        }

        // CUSTOMER SCREEN
        composable(
            route = "${DetailsScreen.Customer.route}/{cIdentifier}",
            arguments = listOf(navArgument("cIdentifier") { type = NavType.StringType })
        ) { backStackEntry ->
            val customerIdentifier = backStackEntry.arguments?.getString("cIdentifier")
            CustomerDetailActions(customerIdentifier = customerIdentifier, navController)
        }
        // CUSTOMER SCREEN (Without Arguments)
        composable(
            route = DetailsScreen.Customer.route,
        ) {
            CustomerDetailActions(customerIdentifier = null, navController)
        }

        // ITEMS SCREEN
        composable(
            route = "${DetailsScreen.Item.route}/{iCode}",
            arguments = listOf(navArgument("iCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemCode = backStackEntry.arguments?.getString("iCode")
            ProductsDetailScreenActions(itemId = itemCode, navController)
        }
        //ITEMS SCREEN (Without Arguments)
        composable(route = DetailsScreen.Item.route) {
            ProductsDetailScreenActions(itemId = null, navController = navController)
            navController.popBackStack( //todo: check this
                route = DetailsScreen.Item.route,
                inclusive = false
            )
        }
    }
}


