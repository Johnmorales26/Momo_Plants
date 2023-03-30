package login

import cart.Cart
import entities.UserEntity
import utils.PlantsUtils.cleanScreen
import utils.PlantsUtils.showPlantsCatalogue
import utils.PlantsUtils.sleep

class UserInterface {
    private val servicio = Service()

    fun screenSplash() {
        Cart.cart.getPlantsByCoroutines()
        println("Bienvenido a la aplicación!")
        var opcion: Int
        do {
            println()
            println("1. Iniciar sesión")
            println("2. Crear cuenta")
            println("3. Salir")
            print("Selecciona una opción: ")
            opcion = readln().toInt()
            sleep()
            cleanScreen()
            when (opcion) {
                1 -> screenSignIn()
                2 -> screenSignUp()
                3 -> println("Hasta luego!")
                else -> println("Opción inválida. Inténtalo de nuevo.")
            }
        } while (opcion != 3)
    }

    private fun screenSignIn() {
        println("Ingresa tus credenciales para iniciar sesión")
        print("Nombre de usuario: ")
        val username = readln()
        print("Contraseña: ")
        val password = readln()
        servicio.login(UserEntity(username, password))
        if (servicio.authenticatedUser) {
            println("Inicio de sesión exitoso!")
            screenMenu()
        }
        sleep()
        cleanScreen()
    }

    private fun screenSignUp() {
        println("Crea una cuenta para iniciar sesión")
        print("Nombre de usuario: ")
        val username = readln()
        if (servicio.userExist(username)) {
            println("Ese nombre de usuario ya está en uso. Inténtalo de nuevo.")
        } else {
            print("Contraseña: ")
            val password = readln()
            servicio.userRegister(UserEntity(username, password))
            println("Cuenta creada exitosamente! Ahora puedes iniciar sesión.")
        }
        sleep()
        cleanScreen()
    }

    private fun screenMenu() {
        showPlantsCatalogue()
        print("Presiona enter para continuar -> ")
        readln()
        while (true) {
            println("BIENVENIDO A MOMO PLANTS")
            println("Menu principal Carrito de compras:")
            Cart.cart.showMenu()
            println("Ingresa una opción: ")
            val prompt = readlnOrNull()?.toIntOrNull()
            when (prompt) {
                1 -> Cart.cart.addToCart()
                2 -> Cart.cart.askPlantToFind()
                3 -> Cart.cart.updatePlantInShoppingCart()
                4 -> Cart.cart.showPlantsCart()
                5 -> showPlantsCatalogue()
                6 -> Cart.cart.checkOut()
                7 -> Cart.cart.showOldOrders()
                8 -> break

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
}