package br.com.alura.panucci.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.ui.screens.MenuListScreen
import br.com.alura.panucci.ui.viewmodels.MenuViewModel

internal const val menuRoute = "Menu"
fun NavGraphBuilder.menuNavigation(navController: NavHostController) {
    composable(route = menuRoute) {
        val viewModel: MenuViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()

        MenuListScreen(uiState = uiState, menuClick = {
            navController.navigateToDetails(it.iD)
        })
    }
}

fun NavController.navigateToMenu(){
    navigate(route = menuRoute){
        launchSingleTop
        popUpTo(menuRoute)
    }
}