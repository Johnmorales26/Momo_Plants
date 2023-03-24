package cart

import UserInterfaceUtils.Companion.sleep
import UserInterfaceUtils.Companion.cleanScreen

fun main() {
    //Instancia de carrito de compras
    val cart = Cart()

    while (true) {
        println("BIENVENIDO A MOMO PLANTS")
        println("Menu principal Carrito de compras:")
        cart.showMenu()
        println("Ingresa una opción: ")
        val prompt = readlnOrNull()?.toIntOrNull()
        when (prompt) {
            1 -> cart.askPlantToAdd()
            2 -> cart.askPlantToFind()
            3 -> cart.askPlantToRemove()
            4 -> cart.showPlantsCart()
            5 -> cart.showPlantsCatalogue()
            6 -> break
            else -> println("Opción inválida.")
        }
        if (prompt == 4 || prompt == 5) {
            print("Presiona enter para continuar -> ")
            readlnOrNull()?.toIntOrNull()
        }
        sleep()
        cleanScreen()
    }
}