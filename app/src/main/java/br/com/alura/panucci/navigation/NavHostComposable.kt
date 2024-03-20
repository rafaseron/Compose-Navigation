package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.CheckoutScreen
import br.com.alura.panucci.ui.screens.DrinksListScreen
import br.com.alura.panucci.ui.screens.HighlightsListScreen
import br.com.alura.panucci.ui.screens.MenuListScreen
import br.com.alura.panucci.ui.screens.ProductDetailsScreen

@Composable
fun NavHostComposable(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppDestination.Highlights.route) {
        composable(route = AppDestination.Highlights.route) {
            HighlightsListScreen(products = sampleProducts, onOrderClick = { navController.navigate(AppDestination.Checkout.route) },
                onProductClick = {
                    navController.navigate("${AppDestination.ProductDetail.route}/${it.iD}") })

            /*LAUNCHED EFFECT SERVE PARA NAVEGAR PARA UMA NOVA TELA, tanto apos certo periodo de tempo,
            quanto por base de uma condição ou evento
             */

            //LaunchedEffect(Unit, block = {})
            // ou
            //LaunchedEffect(Unit){  }

            // Exemplo de uso:
            //LaunchedEffect(Unit, block = { navController.navigate(route = "checkout") delay(3000L) })

        }
        composable(route = AppDestination.Checkout.route) {
            CheckoutScreen(products = sampleProducts, onOrderClick = {navController.popBackStack() /* OU navController.navigateUp()*/})
        }

        composable(route = AppDestination.Menu.route) {
            MenuListScreen(products = sampleProducts, menuClick = {
                navController.navigate(route = "${AppDestination.ProductDetail.route}/${it.iD}")})
        }

        composable(route = AppDestination.Drinks.route) {
            DrinksListScreen(products = sampleProducts, drinkClick = {
                navController.navigate(route = "${AppDestination.ProductDetail.route}/${it.iD}")})
        }
        composable(route = "${AppDestination.ProductDetail.route}/{productId}") {
            val id = it.arguments?.getString("productId") //puxa o valor recebido por parametro em {productId}

            sampleProducts.find {
                    p ->
                p.iD == id //procura na lista de dados qual Produto tem o mesmo valor de iD do que o {productId} que foi recebido por parametro
            }?.let {
                // como o find retorna um valor possivelmente nulo, acessamos com let para mandar o Produto nao nulo na chamada da tela
                    product ->
                ProductDetailsScreen(product = product, onClick = {navController.navigate(route = AppDestination.Checkout.route)})
            } ?: //navController.popBackStack()  OU:
            LaunchedEffect(Unit) {
                navController.navigateUp()  //navigateUp tem Side Effects por recomposicao, entao precisa ser executado dentro de LaunchedEffect
            }
        }
    }

}