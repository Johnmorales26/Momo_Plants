package login

import Catalogue
import Plant

class UserInterface {
    private val servicio = Service()
    private var usuarioAutenticado: Boolean = false

    private fun cleanScreen() {
        repeat (50) {
            print("\n")
        }
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
            when (opcion) {
                1 -> seeLogin()
                2 -> seeRegister()
                3 -> println("Hasta luego!")
                else -> println("Opción inválida. Inténtalo de nuevo.")
            }
        } while (opcion != 3)
    }

    fun seeLogin() {
        cleanScreen()
        println("Ingresa tus credenciales para iniciar sesión")
        print("Nombre de usuario: ")
        val nombreUsuario = readLine()!!
        print("Contraseña: ")
        val contraseña = readLine()!!

        servicio.login(User(nombreUsuario, contraseña))
        usuarioAutenticado = servicio.authenticatedUser
        if (usuarioAutenticado) {
            println("Inicio de sesión exitoso!")
            seePrincipalMenu()
        }
    }

    private fun seePrincipalMenu() {
        for (plant: Plant in Catalogue.plants) {
            println("${plant.stock} x ${plant.name} - Precio unitario: ${plant.price}")
        }
    }

    fun seeRegister() {
        println("Crea una cuenta para iniciar sesión")
        print("Nombre de usuario: ")
        val nombreUsuario = readLine()!!
        if (servicio.existeUsuario(nombreUsuario)) {
            println("Ese nombre de usuario ya está en uso. Inténtalo de nuevo.")
            return
        }
        print("Contraseña: ")
        val contraseña = readLine()!!
        servicio.registrarUsuario(nombreUsuario, contraseña)
        println("Cuenta creada exitosamente! Ahora puedes iniciar sesión.")
    }
}