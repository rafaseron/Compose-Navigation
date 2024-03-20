package br.com.alura.panucci.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController){
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