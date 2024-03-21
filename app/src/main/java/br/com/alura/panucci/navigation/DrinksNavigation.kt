package br.com.alura.panucci.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.DrinksListScreen
import br.com.alura.panucci.ui.viewmodels.DrinksViewModel

internal const val drinksRoute = "Bebidas"
fun NavGraphBuilder.drinksListScreen(navController: NavHostController) {
    composable(route = drinksRoute) {
        val viewModel: DrinksViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()

        DrinksListScreen(uiState = uiState, drinkClick = {
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