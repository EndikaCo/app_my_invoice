package com.endcodev.myinvoice.presentation.compose.screens.home

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.endcodev.myinvoice.domain.models.common.NavBarItem
import com.endcodev.myinvoice.presentation.compose.components.MyNavigationBar
import com.endcodev.myinvoice.presentation.navigation.HomeNavGraph
import com.endcodev.myinvoice.presentation.navigation.Routes.*

/**
 * Home screen for the app.
 */
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        content = { paddingValues -> HomeNavGraph(navController = navController, paddingValues) },
        bottomBar = { NavigationBar(navController = navController) },
    )
}

/**
 * Navigation Bar for the app.
 */
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
            hasNews = true,
            badgeCount = 4
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
        MyNavigationBar(
            navController = navController,
            currentDestination = currentDestination,
            items = items,
            selectedIndex = selectedIndex
        )
    }
}

/**
 * Creates a badge for a navigation bar item.
 *
 * @param item The NavBarItem for which the badge is being created. It contains properties like badgeCount, hasNews, selectedIcon, unselectedIcon, and route.
 * @param index The index of the NavBarItem in the list of navigation bar items.
 * @param selectedItemIndex The index of the currently selected NavBarItem in the navigation bar.
 */
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

