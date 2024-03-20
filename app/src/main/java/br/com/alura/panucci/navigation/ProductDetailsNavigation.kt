package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen

internal const val productDetailsRoute = "ProductDetail"
private const val productId = "productId"
fun NavGraphBuilder.productDetailsScreen(navController: NavHostController){
    composable(route = "$productDetailsRoute/{$productId}") {
        //tem que tomar cuidado que a estrutura de rotas é {productId}
        // ou seja, usando o sistema de concatenacao de string, temos que ter a variavel
        //productId dentro de { } e separado da rota por uma /
        navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getString(productId) //puxa o valor recebido por parametro em {productId}

        sampleProducts.find {
                p ->
            p.iD == id //procura na lista de dados qual Produto tem o mesmo valor de iD do que o {productId} que foi recebido por parametro

        }?.let {
            // como o find retorna um valor possivelmente nulo, acessamos com let para mandar o Produto nao nulo na chamada da tela
                product ->
            ProductDetailsScreen(product = product, onClick = {navController.navigateToCheckout()})
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