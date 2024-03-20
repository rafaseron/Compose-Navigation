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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.panucci.navigation.NavHostComposable
import br.com.alura.panucci.navigation.navigateToCheckout
import br.com.alura.panucci.navigation.navigateToDetails
import br.com.alura.panucci.navigation.navigateToDrinks
import br.com.alura.panucci.navigation.navigateToHighLightScreen
import br.com.alura.panucci.navigation.navigateToMenu
import br.com.alura.panucci.sampledata.bottomAppBarItems
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.theme.PanucciTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            fun routeFlow(navItem: BottomAppBarItem, navController: NavController, productId: String = ""): Unit{
                return when(navItem.label){
                    "Destaques" -> navController.navigateToHighLightScreen()
                    "Checkout" -> navController.navigateToCheckout()
                    "Menu" -> navController.navigateToMenu()
                    "Bebidas" -> navController.navigateToDrinks()
                    "ProductDetail" -> navController.navigateToDetails(productId = productId)
                    else -> navController.navigateToHighLightScreen()
                }
                /* TODO adicionar launchSingleTop e popUpTo para as rotas (porque o routeFlow é usado no BottomBar)
                navController.navigate(route = navItem.label){
                    launchSingleTop = true
                    popUpTo(route = navItem.label)
                }
                 */
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
                                bottomBarItem.destination == navDestination.route //TODO verificar se eese codigo nao deu merda
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

                            routeFlow(navItem, navController)
                            //TAMBEM DA PRA FAZER COMO:
                            // navController.navigate(route = navItem.label)
                            /* lembrando que então o Label da ListOf<BottomAppBarItem> e as String das route (rotas do navHost)
                            devem ter o mesmo valor (se "Destaques" for com o D maiusculo, os dois devem seguir o mesmo padrao
                             */
                        }, onFabClick = { navController.navigateToCheckout() }) {
                    /*content Scope do PanucciApp*/
                        // JA QUE ESSE EH O CONTENT SCOPE DO PANUCCIAPP (E EH PASSADO POR MEIO DE LAMBDA), ELE DEVE SER O ULTIMO ARGUMENTO A SER PASSADO PRO PARAMETRO
                        // (E ULTIMO PARAMETRO ESPERADO NO COMPOSABLE)

                        NavHostComposable(navController = navController)

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