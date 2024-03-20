package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavHostComposable(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppDestination.Highlights.route) {
        hightlightListScreen(navController)
        checkoutScreen(navController)
        menuListScreen(navController)
        drinksListScreen(navController)
        productDetailsScreen(navController)
    }

}