package br.com.alura.panucci

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
                if (navItem.label == "Destaques"){
                    navController.navigate(route = "Destaques"){
                        launchSingleTop = true //nao cria novas instancias de uma rota que está no topo da BackStack
                        popUpTo(route = "Destaques") //remove todos as telas anteriores que estavam na BackStack
                        // o combo de launchSingleTop + popUpTo ->
                        //  faz com que cada rota seja adicionada apenas uma vez para a Pilha.
                        //  Quando 'repetida' uma rota que ja esta dentro da BackStack, faz com que dê Pop em todos elementos anteriores
                    }
                }else{
                    if (navItem.label == "Menu"){
                        navController.navigate(route = "Menu")
                    }else{
                        if (navItem.label == "Bebidas"){
                            navController.navigate(route = "Bebidas")
                        }
                    }
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
                                bottomBarItem.route == navDestination.route
                            }

                        } ?: bottomAppBarItems.first()

                        mutableStateOf(item)
                    }

                    PanucciApp(
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
                        }, onFabClick = { navController.navigate("Checkout") }) {
                    /*content Scope do PanucciApp*/
                        // TODO implementar o navHost
                        NavHost(navController = navController, startDestination = "Destaques") {
                            composable(route = "Destaques") {
                                HighlightsListScreen(products = sampleProducts, onOrderClick = { navController.navigate("Checkout") },
                                    onProductClick = { navController.navigate("ProductDetail") })

                                /*LAUNCHED EFFECT SERVE PARA NAVEGAR PARA UMA NOVA TELA, tanto apos certo periodo de tempo,
                                quanto por base de uma condição ou evento
                                 */

                                //LaunchedEffect(Unit, block = {})
                                // ou
                                //LaunchedEffect(Unit){  }

                                // Exemplo de uso:
                                //LaunchedEffect(Unit, block = { navController.navigate(route = "checkout") delay(3000L) })

                            }
                            composable(route = "Checkout") {
                                CheckoutScreen(products = sampleProducts)
                            }

                            composable(route = "Menu") {
                                MenuListScreen(products = sampleProducts)
                            }

                            composable(route = "Bebidas") {
                                DrinksListScreen(products = sampleProducts)
                            }
                            composable(route = "ProductDetail") {
                                ProductDetailsScreen(product = sampleProductWithImage)
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
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Ristorante Panucci")
                },
            )
        },
        bottomBar = {
            PanucciBottomAppBar(
                item = bottomAppBarItemSelected,
                items = bottomAppBarItems,
                onItemChange = onBottomAppBarItemSelectedChange,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick
            ) {
                Icon(
                    Icons.Filled.PointOfSale,
                    contentDescription = null
                )
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