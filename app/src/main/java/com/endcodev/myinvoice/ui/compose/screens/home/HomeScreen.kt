package com.endcodev.myinvoice.ui.compose.screens.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.endcodev.myinvoice.ui.navigation.HomeNavGraph
import com.endcodev.myinvoice.ui.navigation.Routes.*
import com.endcodev.myinvoice.ui.compose.screens.home.invoice.InvoicesContent
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        bottomBar = { NavigationBar(navController = navController) },
    ) {
        Log.v("AD", "$it")
        HomeNavGraph(navController = navController)
    }
}

@Composable
fun FloatingActionButton(
    modifier: Modifier,
    painter: Painter,
    onAddButtonClick: () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = {
            onAddButtonClick()
        },
        modifier = modifier,
    ) {
        Icon(
            painter = painter,
            contentDescription = ""
        )
    }
}

@Composable
fun NavigationBar(navController: NavHostController) {

    val items = listOf(
        NavBarItem(
            route = InvoicesContent.routes,
            selectedIcon = Icons.Filled.MailOutline,
            unselectedIcon = Icons.Outlined.MailOutline,
            hasNews = false,
        ),
        NavBarItem(
            route = CustomerContent.routes,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
            badgeCount = 45
        ),
        NavBarItem(
            route = ItemsContent.routes,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
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
        HomeScreen()
    }
}