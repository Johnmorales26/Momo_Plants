package cart
fun main() {
    //Instancia de carrito de compras
    val cart = Cart()
    val shoppingCart:MutableList<Plant> = mutableListOf<Plant>()

    while (true) {
        println("Menu principal Carrito de compras:")
        cart.showMenu()
        println("Ingresa una opción: ")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> cart.addPlant(shoppingCart)
            2 -> cart.findPlant(shoppingCart)
            3 -> cart.removePlant(shoppingCart)
            4 -> cart.showPlants(shoppingCart)
            5 -> break
            else -> println("Opción inválida.")
        }
    }
}
