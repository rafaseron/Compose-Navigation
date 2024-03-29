package br.com.alura.panucci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.alura.panucci.navigation.AppDestination
import br.com.alura.panucci.sampledata.bottomAppBarItems
import br.com.alura.panucci.sampledata.sampleProductWithImage
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.screens.*
import br.com.alura.panucci.ui.theme.PanucciTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            fun routeFlow(navItem: BottomAppBarItem){
                navController.navigate(route = navItem.label){
                    launchSingleTop = true
                    popUpTo(route = navItem.label)
                }
            }

            //Cuidados a serem tomados:
            // As navegações devem ser feitas fora do escopo de Composição
            // As rotas devem ser criadas fora do escopo de Composição
            // Precisamos destes cuidados, principalmente ao usar o LaunchedEffect, para que o app não entre em loop de renderizacão
            PanucciTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val currentBackStack by navController.currentBackStackEntryAsState()
                    val currentDestination = currentBackStack?.destination

                    var selectedItem by remember(currentDestination) {
                        val item = currentDestination?.let {
                            navDestination ->
                            bottomAppBarItems.find {
                                bottomBarItem ->
                                bottomBarItem.destination.route == navDestination.route
                            }

                        } ?: bottomAppBarItems.first()

                        mutableStateOf(item)
                    }

                    fun showBottomBar(): Boolean{
                        currentDestination?.let {
                         p ->
                            when (p.route.toString()){
                                "Destaques" -> return true
                                "Menu" -> return true
                                "Bebidas" -> return true
                                else -> return false
                            }
                        } ?: return false
                    }

                    fun showTopBar(): Boolean{
                        currentDestination?.let {
                            p ->
                            when (p.route.toString()){
                                "Destaques" -> return true
                                else -> return false
                            }
                        } ?: return false
                    }

                    fun showFAB(): Boolean {
                        currentDestination?.let {
                            p ->
                            when (p.route.toString()){
                                "Menu" -> return true
                                "Bebidas" -> return true
                                else -> return false
                            }
                        } ?: return false
                    }

                    PanucciApp(
                        showBottomBar = showBottomBar(),
                        showTopBar = showTopBar(),
                        showFAB = showFAB(),
                        bottomAppBarItemSelected = selectedItem,
                        onBottomAppBarItemSelectedChange = {
                            navItem ->
                            selectedItem = navItem

                            routeFlow(navItem)
                            //TAMBEM DA PRA FAZER COMO:
                            // navController.navigate(route = navItem.label)
                            /* lembrando que então o Label da ListOf<BottomAppBarItem> e as String das route (rotas do navHost)
                            devem ter o mesmo valor (se "Destaques" for com o D maiusculo, os dois devem seguir o mesmo padrao
                             */
                        }, onFabClick = { navController.navigate(AppDestination.Checkout.route) }) {
                    /*content Scope do PanucciApp*/
                        // JA QUE ESSE EH O CONTENT SCOPE DO PANUCCIAPP (E EH PASSADO POR MEIO DE LAMBDA), ELE DEVE SER O ULTIMO ARGUMENTO A SER PASSADO PRO PARAMETRO
                        // (E ULTIMO PARAMETRO ESPERADO NO COMPOSABLE)

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
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanucciApp(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    onFabClick: () -> Unit = {},
    showTopBar: Boolean = false,
    showBottomBar: Boolean = false,
    showFAB: Boolean = false,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            if(showTopBar){
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Ristorante Panucci")
                    },
                )
            }

        },
        bottomBar = {
            if (showBottomBar){
                PanucciBottomAppBar(
                    item = bottomAppBarItemSelected,
                    items = bottomAppBarItems,
                    onItemChange = onBottomAppBarItemSelectedChange,
                )
            }
        },
        floatingActionButton = {
            if(showFAB){
                FloatingActionButton(
                    onClick = onFabClick
                ) {
                    Icon(
                        Icons.Filled.PointOfSale,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PanucciAppPreview() {
    PanucciTheme {
        Surface {
            PanucciApp {}
        }
    }
}