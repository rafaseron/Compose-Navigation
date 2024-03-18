package br.com.alura.panucci.navigation

data class AppRoutes (val highlights: String = "Destaques", val checkout: String = "Checkout",
    val menu:String = "Menu", val drinks: String = "Bebidas", val productDetail: String = "ProductDetail")
// A implementacao AppRoutes da o mesmo resultado que AppDestination

sealed class AppDestination (val route: String){
    object Highlights: AppDestination(route = "Destaques")
    object Checkout: AppDestination(route = "Checkout")
    object Menu: AppDestination(route = "Menu")
    object Drinks: AppDestination(route = "Bebidas")
    object ProductDetail: AppDestination(route = "ProductDetail")

}
