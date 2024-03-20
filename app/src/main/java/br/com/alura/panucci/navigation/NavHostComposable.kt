package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation

@Composable
fun NavHostComposable(navController: NavHostController) {
    NavHost(navController = navController, startDestination = homeGraph) {
        //no startDestination ele acessa o homeGraph e é o startDestination do homeGraph quem determina qual é o startDestination
        /*
        Assim, direto, é como era, antes de Aninhar os Graphos de Navegação
        highlightListScreen(navController)
        menuListScreen(navController)
        drinksListScreen(navController)
         */
        homeGraph(navController)    //essa tecnica de Aninhar Graphos é complexa, mas tu tem que saber que ela existe - porque vai tu trabalhe em um app que seja assim

        productDetailsScreen(navController)
        checkoutScreen(navController)
    }
}

// NO CASO, ESTES DADOS ABAIXO PODEM ESTAR EM UM OUTRO ARQUIVO, POR EXEMPLO HomeGraphNavigation.kt
internal const val homeGraph = "homeGraphRoute"
fun NavGraphBuilder.homeGraph(navController: NavHostController){
    navigation(route = homeGraph, startDestination = highlightListRoute){
        highlightListScreen(navController)
        menuListScreen(navController)
        drinksListScreen(navController)
    }
}