package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.navigation.navigateToCheckout

internal const val productDetailsRoute = "ProductDetail"
private const val productId = "productId"
fun NavGraphBuilder.productDetailsScreen(navController: NavHostController){
    composable(route = "$productDetailsRoute/${productId}") {
        navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getString(productId) //puxa o valor recebido por parametro em {productId}
        Log.e("Goiaba", "backStackId -> $id")

        sampleProducts.find {
                p ->
            p.iD == id //procura na lista de dados qual Produto tem o mesmo valor de iD do que o {productId} que foi recebido por parametro

        }?.let {
            // como o find retorna um valor possivelmente nulo, acessamos com let para mandar o Produto nao nulo na chamada da tela
                product ->
            Log.e("Goiaba", "valor do p.id -> ${product.iD} valor do id -> $id")
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
        Log.e("Goiaba", " productId -> $productId e rota -> $rota")
        //launchSingleTop
        //popUpTo(productDetailsRoute)
    }
}