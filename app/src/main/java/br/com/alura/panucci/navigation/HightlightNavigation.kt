package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.HighlightsListScreen

private const val highlightListRoute = "Destaques" //'const val' porque 'const' nao é processada -> o build trata como um valor sempre conhecido, nao tenta ler de novo
fun NavGraphBuilder.highlightListScreen(navController: NavHostController) {
    composable(route = highlightListRoute) {
        HighlightsListScreen(products = sampleProducts,
            onOrderClick = { navController.navigateToCheckout() },
            onProductClick = {
                navController.navigateToDetails(it.iD)
            })
    }
}

fun NavController.navigateToHighLightScreen(){
    navigate(route = highlightListRoute)
}
//funcao global que oferecemos para seja feita a navegacao para este destino