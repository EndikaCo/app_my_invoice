package com.endcodev.myinvoice.domain.models

import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)