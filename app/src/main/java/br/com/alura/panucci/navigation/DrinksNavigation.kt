package br.com.alura.panucci.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.DrinksListScreen

fun NavGraphBuilder.drinksListScreen(navController: NavHostController) {
    composable(route = AppDestination.Drinks.route) {
        DrinksListScreen(products = sampleProducts, drinkClick = {
            navController.navigate(route = "${AppDestination.ProductDetail.route}/${it.iD}")
        })
    }
}