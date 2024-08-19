package com.example.bimber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bimber.data.model.deserializeGif
import com.example.bimber.ui.gif_details.GifDetailsScreen
import com.example.bimber.ui.search.SearchScreen
import com.example.bimber.ui.theme.BimberTheme
import com.example.bimber.ui.trending.TrendingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BimberTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationGraph(navController, Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    BottomNavigation {
        val items = listOf(
            BottomNavItem.Trending,
            BottomNavItem.Categories,
            BottomNavItem.Search
        )
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = BottomNavItem.Trending.route, modifier = modifier) {
        composable(BottomNavItem.Trending.route) {
            TrendingScreen(navController)
        }
        composable(BottomNavItem.Categories.route) {
            CategoriesScreen()
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen(navController)
        }
        composable("details/{gifJson}") { backStackEntry ->
            val gifJson =
                backStackEntry.arguments?.getString("gifJson") ?: throw IllegalArgumentException()
            val gif = deserializeGif(gifJson)
            GifDetailsScreen(gif = gif, navController = navController)
        }
    }
}