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
                    navController.navigate(route = "destaques")
                }else{
                    if (navItem.label == "Menu"){
                        navController.navigate(route = "menu")
                    }else{
                        if (navItem.label == "Bebidas"){
                            navController.navigate(route = "bebidas")
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
                    var selectedItem by remember {
                        val item = bottomAppBarItems.first()
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
                        }, onFabClick = { navController.navigate("checkout") }) {
                    /*content Scope do PanucciApp*/
                        // TODO implementar o navHost
                        NavHost(navController = navController, startDestination = "destaques") {
                            composable(route = "destaques") {
                                HighlightsListScreen(products = sampleProducts, onOrderClick = { navController.navigate("checkout") },
                                    onProductClick = { navController.navigate("productDetail") })

                                /*LAUNCHED EFFECT SERVE PARA NAVEGAR PARA UMA NOVA TELA, tanto apos certo periodo de tempo,
                                quanto por base de uma condição ou evento
                                 */

                                //LaunchedEffect(Unit, block = {})
                                // ou
                                //LaunchedEffect(Unit){  }

                                // Exemplo de uso:
                                //LaunchedEffect(Unit, block = { navController.navigate(route = "checkout") delay(3000L) })

                            }
                            composable(route = "checkout") {
                                CheckoutScreen(products = sampleProducts)
                            }

                            composable(route = "menu") {
                                MenuListScreen(products = sampleProducts)
                            }

                            composable(route = "bebidas") {
                                DrinksListScreen(products = sampleProducts)
                            }
                            composable(route = "productDetail") {
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