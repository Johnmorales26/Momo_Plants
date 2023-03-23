package login

import Catalogue
import UserInterfaceUtils.Companion.sleep
import UserInterfaceUtils.Companion.cleanScreen
import UserInterfaceUtils.Companion.showPlantsCatalogue

class UserInterface {
    private val servicio = Service()

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
        showPlantsCatalogue()

        print("Presiona enter para continuar -> ")
        readLine()!!
    }

    fun seeRegister() {
        println("Crea una cuenta para iniciar sesión")
        print("Nombre de usuario: ")
        val nombreUsuario = readLine()!!
        if (servicio.existeUsuario(nombreUsuario)) {
            println("Ese nombre de usuario ya está en uso. Inténtalo de nuevo.")
        } else {
            print("Contraseña: ")
            val contraseña = readLine()!!
            servicio.registrarUsuario(User(nombreUsuario, contraseña))
            println("Cuenta creada exitosamente! Ahora puedes iniciar sesión.")
        }
        sleep()
        cleanScreen()
    }
}