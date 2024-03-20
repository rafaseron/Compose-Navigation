package br.com.alura.panucci.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.HighlightsListScreen

fun NavGraphBuilder.highlightListScreen(navController: NavHostController) {
    composable(route = AppDestination.Highlights.route) {
        HighlightsListScreen(products = sampleProducts,
            onOrderClick = { navController.navigate(AppDestination.Checkout.route) },
            onProductClick = {
                navController.navigate("${AppDestination.ProductDetail.route}/${it.iD}")
            })
    }
}