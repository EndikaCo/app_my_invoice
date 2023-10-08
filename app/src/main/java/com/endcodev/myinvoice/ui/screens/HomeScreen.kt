package com.endcodev.myinvoice.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.endcodev.myinvoice.ui.navigation.DetailsScreen
import com.endcodev.myinvoice.ui.navigation.Graph
import com.endcodev.myinvoice.ui.navigation.HomeNavGraph
import com.endcodev.myinvoice.ui.navigation.Routes
import com.endcodev.myinvoice.ui.screens.invoice.InvoicesContent
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        bottomBar = { NavigationBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(onButtonClick = {

            val currentRoute = navController.currentBackStackEntry?.destination?.route

            if(currentRoute == Routes.CustomerContent.routes)
                navController.navigate(DetailsScreen.Overview.route)

            else if(currentRoute == Routes.InvoicesContent.routes)
                navController.navigate(Graph.DETAILS)

            else if(currentRoute == Routes.ItemsContent.routes)
                navController.navigate(Graph.DETAILS)

        })
        }
    ) {
        Log.v("AD", "$it")
        HomeNavGraph(navController = navController)
    }
}

@Composable
fun FloatingActionButton(
    onButtonClick: () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = {
            onButtonClick()
        }, modifier = Modifier.height(50.dp)
    ) {
        Text("add", Modifier.padding(start = 10.dp, end = 10.dp))
    }
}

@Composable
fun NavigationBar(navController: NavHostController) {

    val items = listOf(
        NavBarItem(
            route = Routes.InvoicesContent.routes,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        NavBarItem(
            route = Routes.CustomerContent.routes,
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews = false,
            badgeCount = 45
        ),
        NavBarItem(
            route = Routes.ItemsContent.routes,
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = true,
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val selectedIndex = items.indexOfFirst { it.route == currentDestination?.route }

    val bottomBarDestination = items.any { bottomItem ->
        bottomItem.route == currentDestination?.route
    }

    if (bottomBarDestination) {
        androidx.compose.material3.NavigationBar {

            items.forEachIndexed { index, navItem ->

                NavigationBarItem(
                    label = { Text(text = navItem.route) },
                    icon = { ItemBadge(navItem, index, selectedIndex) },
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    selected = currentDestination?.hierarchy?.any { navDestination ->
                        navDestination.route == navItem.route
                    } == true,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemBadge(item: NavBarItem, index: Int, selectedItemIndex: Int) {
    BadgedBox(
        badge = {
            if (item.badgeCount != null)
                Badge { Text(text = item.badgeCount.toString()) }
            else if (item.hasNews)
                Badge()
        }
    ) {
        Icon(
            imageVector =
            if (index == selectedItemIndex)
                item.selectedIcon
            else
                item.unselectedIcon,
            contentDescription = item.route
        )
    }
}

data class NavBarItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@Preview
@Composable
fun HomePreview() {
    MyInvoiceTheme {
        InvoicesContent()
    }
}