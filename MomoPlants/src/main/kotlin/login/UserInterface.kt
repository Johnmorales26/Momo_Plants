package login

import Catalogue
import Plant

class UserInterface {
    private val servicio = Service()

    private fun cleanScreen() {
        repeat (50) {
            print("\n")
        }
    }

    private fun sleep() {
        Thread.sleep(1000)
    }

    fun seeMenu() {
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
                1 -> seeLogin()
                2 -> seeRegister()
                3 -> println("Hasta luego!")
                else -> println("Opción inválida. Inténtalo de nuevo.")
            }
        } while (opcion != 3)
    }

    fun seeLogin() {
        println("Ingresa tus credenciales para iniciar sesión")
        print("Nombre de usuario: ")
        val nombreUsuario = readLine()!!
        print("Contraseña: ")
        val contraseña = readLine()!!

        servicio.login(User(nombreUsuario, contraseña))
        if (servicio.authenticatedUser) {
            println("Inicio de sesión exitoso!")
            seePrincipalMenu()
        }
        sleep()
        cleanScreen()
    }

    private fun seePrincipalMenu() {
        for (plant: Plant in Catalogue.plants) {
            println("${plant.stock} x ${plant.name} - Precio unitario: ${plant.price}")
        }
    }

    fun seeRegister() {
        cleanScreen()
        println("Crea una cuenta para iniciar sesión")
        print("Nombre de usuario: ")
        val nombreUsuario = readLine()!!
        if (servicio.existeUsuario(nombreUsuario)) {
            println("Ese nombre de usuario ya está en uso. Inténtalo de nuevo.")
            sleep()
            cleanScreen()
            return
        }
        print("Contraseña: ")
        val contraseña = readLine()!!
        servicio.registrarUsuario(User(nombreUsuario, contraseña))
        println("Cuenta creada exitosamente! Ahora puedes iniciar sesión.")
        sleep()
        cleanScreen()
    }
}