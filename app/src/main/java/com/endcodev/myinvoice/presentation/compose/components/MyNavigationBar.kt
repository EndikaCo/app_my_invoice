package com.endcodev.myinvoice.presentation.compose.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.endcodev.myinvoice.domain.models.common.NavBarItem
import com.endcodev.myinvoice.presentation.compose.screens.home.ItemBadge
import com.endcodev.myinvoice.presentation.navigation.Routes
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme

@Composable
fun MyNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    items: List<NavBarItem>,
    selectedIndex: Int
) {
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


@Preview(name = "Light Mode", backgroundColor = 0xFFC2C2C2)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyNavigationBar() {

    val items = listOf(
        NavBarItem(
            route = Routes.InvoicesContent.routes,
            selectedIcon = Icons.Filled.MailOutline,
            unselectedIcon = Icons.Outlined.MailOutline,
            hasNews = false,
        ),
        NavBarItem(
            route = Routes.CustomerContent.routes,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = true,
            badgeCount = 4
        ),
        NavBarItem(
            route = Routes.ItemsContent.routes,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            hasNews = true,
        ),
    )

    MyInvoiceTheme {
        MyNavigationBar(rememberNavController(), null, items, 1)
    }
}
