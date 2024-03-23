package br.com.alura.panucci.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightListRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.ui.theme.PanucciTheme

val listBottomAppBarItems = listOf(
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

class BottomAppBarItem(
    val label: String,
    val icon: ImageVector,
    val destination: String
)

@Composable
fun PanucciBottomAppBar(
    receivedSelectedItem: BottomAppBarItem,
    modifier: Modifier = Modifier,
    listItems: List<BottomAppBarItem> = emptyList(),
    onItemClicked: (BottomAppBarItem) -> Unit = {}
) {
    NavigationBar(modifier) {
        listItems.forEach {
            item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = receivedSelectedItem.destination == item.destination,
                onClick = {
                    onItemClicked(item)
                }
            )
        }
    }
}

@Preview
@Composable
fun PanucciBottomAppBarPreview() {
    PanucciTheme {
        PanucciBottomAppBar(
            receivedSelectedItem = listBottomAppBarItems.first(),
            listItems = listBottomAppBarItems
        )
    }
}