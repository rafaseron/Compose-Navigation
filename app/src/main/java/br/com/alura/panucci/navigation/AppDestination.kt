package br.com.alura.panucci.navigation
data class RouteShape (val route: String)

class Destination{
    val hightlights = RouteShape(route = "Destaques")
    val checkout = RouteShape(route = "Checkout")
    val menu = RouteShape(route = "Menu")
    val bebidas = RouteShape(route = "Bebidas")
    val productDetail = RouteShape(route = "ProductDetail")
}

//Essa implementacao RouteShape + Destination da o mesmo resultado que a AppDestination.
// Use a implementacao que preferir.

sealed class AppDestination (val route: String){
    object Highlights: AppDestination(route = "Destaques")
    object Checkout: AppDestination(route = "Checkout")
    object Menu: AppDestination(route = "Menu")
    object Drinks: AppDestination(route = "Bebidas")
    object ProductDetail: AppDestination(route = "ProductDetail")

}
