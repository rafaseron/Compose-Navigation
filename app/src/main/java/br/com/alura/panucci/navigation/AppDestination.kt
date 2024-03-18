package br.com.alura.panucci.navigation

/*
data class RouteShape(val route: String)

sealed class AppDestination (){
    val checkout = RouteShape(route = "checkout")
}

A implementacao abaixo da o mesmo resultado que essa implementacao acima de usar o RouteShape para fazer os AppDestination
 */


sealed class AppDestination (val route: String){
    object Highlights: AppDestination(route = "Destaques")
    object Checkout: AppDestination(route = "Checkout")
    object Menu: AppDestination(route = "Menu")
    object Drinks: AppDestination(route = "Bebidas")
    object ProductDetail: AppDestination(route = "ProductDetail")

}
