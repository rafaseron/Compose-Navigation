package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.MenuListScreen

private const val menuRoute = "Menu"
fun NavGraphBuilder.menuListScreen(navController: NavHostController) {
    composable(route = menuRoute) {
        MenuListScreen(products = sampleProducts, menuClick = {
            navController.navigate(route = "${AppDestination.ProductDetail.route}/${it.iD}")
        })
    }
}

fun NavController.navigateToMenu(){
    navigate(route = menuRoute)
}