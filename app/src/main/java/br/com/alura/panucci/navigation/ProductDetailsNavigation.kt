package br.com.alura.panucci.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.ui.viewmodels.ProductDetailViewModel

internal const val productDetailsRoute = "ProductDetail"
private const val productId = "productId"
fun NavGraphBuilder.productDetailsScreen(navController: NavHostController){
    composable(route = "$productDetailsRoute/{$productId}") {
        navBackStackEntry ->
        navBackStackEntry.arguments?.getString(productId)?.let {
            val viewModel: ProductDetailViewModel = viewModel()
            viewModel.findByIdWithResponse(it)
            val uiState by viewModel.uiState.collectAsState()

            ProductDetailsScreen(uiState = uiState, onClick = {navController.navigateToCheckout()}, tryAgainClick = {viewModel.findByIdWithResponse(it)}, voltarClick = {navController.popBackStack()})

        } ?: //navController.popBackStack()  OU:
        LaunchedEffect(Unit) {
            navController.navigateToDrinks()
            //navController.navigateUp()  //navigateUp tem Side Effects por recomposicao, entao precisa ser executado dentro de LaunchedEffect
        }
    }
}

fun NavController.navigateToDetails(productId: String){
    val rota = "$productDetailsRoute/$productId"
    navigate(route = rota){
        launchSingleTop
        popUpTo(productDetailsRoute)
    }
}