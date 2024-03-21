package br.com.alura.panucci.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.HighlightsListScreen
import br.com.alura.panucci.ui.viewmodels.HighlightViewModel

internal const val highlightListRoute = "Destaques" //'const val' porque 'const' nao é processada -> o build trata como um valor sempre conhecido, nao tenta ler de novo
fun NavGraphBuilder.highlightListScreen(navController: NavHostController) {
    composable(route = highlightListRoute) {
        val viewModel:HighlightViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()

        HighlightsListScreen(uiState = uiState,
            onOrderClick = { navController.navigateToCheckout() },
            onProductClick = {
                navController.navigateToDetails(it.iD)
            })
    }
}

fun NavController.navigateToHighLightScreen(){
    navigate(route = highlightListRoute){
        launchSingleTop
        popUpTo(highlightListRoute)
    }
}
//funcao global que oferecemos para seja feita a navegacao para este destino