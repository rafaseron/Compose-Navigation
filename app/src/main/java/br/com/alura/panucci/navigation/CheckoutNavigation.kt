package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.CheckoutScreen

private const val checkoutRoute = "Checkout"
fun NavGraphBuilder.checkoutScreen(navController: NavHostController) {
    composable(route = checkoutRoute) {
        CheckoutScreen(
            products = sampleProducts,
            onOrderClick = { navController.popBackStack() /* OU navController.navigateUp()*/ })
    }
}

fun NavController.navigateToCheckout(){
    navigate(route = checkoutRoute)
}