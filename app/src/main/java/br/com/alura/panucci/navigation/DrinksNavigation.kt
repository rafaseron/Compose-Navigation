package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.DrinksListScreen

internal const val drinksRoute = "Bebidas"
fun NavGraphBuilder.drinksListScreen(navController: NavHostController) {
    composable(route = drinksRoute) {
        DrinksListScreen(products = sampleProducts, drinkClick = {
            navController.navigateToDetails(it.iD)
        })
    }
}

fun NavController.navigateToDrinks(){
    navigate(route = drinksRoute){
        launchSingleTop
        popUpTo(drinksRoute)
    }
}