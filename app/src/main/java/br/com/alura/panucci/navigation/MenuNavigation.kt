package br.com.alura.panucci.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.MenuListScreen

fun NavGraphBuilder.menuListScreen(navController: NavHostController) {
    composable(route = AppDestination.Menu.route) {
        MenuListScreen(products = sampleProducts, menuClick = {
            navController.navigate(route = "${AppDestination.ProductDetail.route}/${it.iD}")
        })
    }
}