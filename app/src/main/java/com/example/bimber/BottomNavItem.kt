package com.example.bimber

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    data object Trending : BottomNavItem("Trending", Icons.Filled.Star, "trending")
    data object Categories : BottomNavItem("Categories", Icons.Filled.Menu, "categories")
    data object Search : BottomNavItem("Search", Icons.Filled.Search, "search")
}
