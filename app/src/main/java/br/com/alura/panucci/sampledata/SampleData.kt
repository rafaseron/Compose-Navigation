package br.com.alura.panucci.sampledata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.alura.panucci.model.Product
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightListRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.ui.components.BottomAppBarItem
import java.math.BigDecimal
import kotlin.random.Random

private val loremName = LoremIpsum(Random.nextInt(10)).values.first()
private val loremDesc = LoremIpsum(Random.nextInt(30)).values.first()

val sampleProductWithImage = Product(
    name = LoremIpsum(10).values.first(),
    price = BigDecimal("9.99"),
    description = LoremIpsum(30).values.first(),
    image = "https://picsum.photos/1920/1080"
)

val sampleProductWithoutImage = Product(
    name = LoremIpsum(10).values.first(),
    price = BigDecimal("9.99"),
    description = LoremIpsum(30).values.first(),
)

val sampleProducts = List(10) { index ->
    Product(
        name = loremName,
        price = BigDecimal("9.99"),
        description = loremDesc,
        image = if (index % 2 == 0) "https://picsum.photos/1920/1080" else null
    )
}

val bottomAppBarItems = listOf(
    BottomAppBarItem(
        label = "Destaques",
        icon = Icons.Filled.AutoAwesome,
        destination = highlightListRoute //
    ),
    BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
        destination = menuRoute //
    ),
    BottomAppBarItem(
        label = "Bebidas",
        icon = Icons.Outlined.LocalBar,
        destination = drinksRoute //
    ),
)