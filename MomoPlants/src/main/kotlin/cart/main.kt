package cart
fun main() {
    //Instancia de carrito de compras
    val cart = Cart()
    val shoppingCart: MutableList<Plant> = mutableListOf<Plant>()

    while (true) {
        println("Menu principal Carrito de compras:")
        cart.showMenu()
        println("Ingresa una opción: ")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> cart.addPlant()
            2 -> cart.findPlant()
            3 -> cart.removePlant()
            4 -> cart.showPlants()
            5 -> break
            else -> println("Opción inválida.")
        }
    }
}
