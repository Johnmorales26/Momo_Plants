package cart

import dataAccess.PlantsDatabase
import entities.ItemEntity
import entities.PlantEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import utils.PlantsUtils

class Cart {

    internal val shoppingCart: MutableList<PlantEntity> = mutableListOf()
    private val oldOrders: MutableList<MutableList<PlantEntity>> = mutableListOf()
    private var plants: MutableList<PlantEntity> = mutableListOf()

    companion object {
        val cart = Cart()
    }

    fun getPlantsByFlow() = GlobalScope.launch { PlantsUtils.getAllPlantsByFlow().collect { plants.add(it) } }

    fun showMenu() {
        val menu = listOf(
            Pair(1, "Agregar planta al carrito"),
            Pair(2, "Buscar planta"),
            Pair(3, "Eliminar planta"),
            Pair(4, "Mostrar plantas del carrito"),
            Pair(5, "Mostrar plantas del catálogo"),
            Pair(6, "Finalizar Pedido"),
            Pair(7, "Ver pedidos anteriores"),
            Pair(8, "Salir")
        )
        return menu.forEach { option -> println("${option.first}.- ${option.second}") }
    }

    internal fun addItem(item: PlantEntity) = shoppingCart.add(item)

    fun askPlantToFind() {
        print("Ingresa el nombre de la planta a buscar: ")
        val nameP = readlnOrNull()?.trim()
        if (nameP != null) {
            val item = findItemByName(nameP)

            if (item != null) {
                println("La planta ${item.name} se encuentra en el carrito.")
            } else {
                println("La planta $nameP no se encuentra en el carrito.")
            }
        } else {
            println("Error: datos ingresados inválidos.")
        }
    }

    internal fun findItemByName(plantName: String): PlantEntity? =
        shoppingCart.find { item -> item.name.lowercase().contains(plantName.lowercase()) }

    internal fun findItemByID(idPlant: Int): PlantEntity? = plants.find { item -> item.id == idPlant }

    fun updatePlantInShoppingCart() {
        print("Ingresa el id de planta que deseas Modificar: ")
        val indexP = readlnOrNull()?.trim()?.toIntOrNull()
        if (indexP != null && indexP in 0 until shoppingCart.size) {
            println("Selecciona la acción que deseas realizar:")
            println("1. Agregar")
            println("2. Disminuir")
            when (readlnOrNull()?.trim()?.toIntOrNull()) {
                1 -> addToCart()
                2 -> deleteFromCart()
            }
        } else {
            println("No existe el id")
        }
    }

    fun addToCart() {
        print("Ingresa el id de planta que deseas agregar: ")
        val indexP = readlnOrNull()?.trim()?.toIntOrNull()
        val plant = findItemByID(indexP ?: -1)
        if (plant == null) {
            println("Datos Invalidos")
            return
        }
        try {
            print("Cantidad de plantas que deseas agregar: ")
            val quantity = readlnOrNull()?.toIntOrNull()

            if (quantity == null || quantity < 1) {
                println("Cantidad invalida")
                return
            }
            if (plant in shoppingCart) {
                increaseQuantity(plant, quantity)
            } else {
                plant.quantity = quantity
                shoppingCart.add(plant)
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    internal fun increaseQuantity(plant: PlantEntity, quantity: Int) {
        shoppingCart.find { it.id == plant.id }?.let { it.quantity += quantity }
    }

        /*fun addToCart() {
            print("Ingresa el id de planta que deseas agregar: ")
            val indexP = readlnOrNull()?.trim()?.toIntOrNull()
            val plant = findItemByID(indexP!!)
            if (plant != null) {
                try {
                    print("Cantidad de plantas que deseas agregar: ")
                    val quantity = readlnOrNull()?.toIntOrNull()
                    if (shoppingCart.contains(plant)) {
                        shoppingCart.find { item -> item == plant }?.let {
                            it.quantity += quantity!!
                        }
                    } else {
                        plant.quantity = quantity!!
                        shoppingCart.add(plant)

                    }
                } catch (e: Exception) {
                    println("Error: $e")
                }
            } else {
                println("Datos Invalidos")
            }
        }*/

    fun deleteFromCart() {
        print("Ingresa el id de planta que deseas eliminar: ")
        val indexP = readlnOrNull()?.trim()?.toIntOrNull()
        val plant = shoppingCart.find { item -> item.id == indexP }
        if (plant == null) {
            println("Datos Invalidos")
            return
        }
        try {
            print("Cantidad de plantas que deseas disminuir: ")
            val quantity = readlnOrNull()?.toIntOrNull()
            if (quantity == null || quantity < 1) {
                println("Cantidad invalida")
                return
            }
            if (plant.quantity > quantity) {
                plant.quantity -= quantity
            } else {
                shoppingCart.remove(plant)
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    fun showPlantsCart() {
        if (shoppingCart.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("Plantas en el carrito:")
            shoppingCart.forEach {
                println("Id: ${it.id}, Nombre: ${it.name}, Cantidad: ${it.quantity}, Precio: ${it.price}")
            }
            println("Total ${shoppingCart.size}")
            menuShow()
        }
    }

    private fun menuShow() {
        var opcion: Int
        do {
            println()
            println("1. Edita una planta del carro")
            println("2. Eliminar una planta del carro")
            //println("3. Aplicar cupón")
            println("3. Salir\n")
            print("Selecciona una opción: ")
            println()
            opcion = readln().toInt()
            when (opcion) {
                1 -> updatePlant()
                2 -> updatePlantInShoppingCart()
                //3 -> println("3!")
                3 -> println("Has regresado al menu principal")
                else -> println("Opción inválida. Inténtalo de nuevo.")
            }
        } while (opcion != 3)
    }

    private fun updatePlant() {
        println("Que accion desea realizar?")
        println("1. Aumentar")
        println("2. Disminuir")
        val option = readlnOrNull()?.toIntOrNull()
        when(option) {
            1 -> addToCart()
            2 -> deleteFromCart()
            3 -> println("Opcion Invalida")
        }
    }

    internal fun updateItem(item: PlantEntity, index: Int) {
        shoppingCart[index] = item
    }

    fun showOldOrders() {
        if (oldOrders.isEmpty()) {
            println("No hay ordenes antiguas.")
        } else {
            println("Ordenes antiguas:")
            println(oldOrders.size)
            println("Orden No. $1 -----")
            println(oldOrders)
            oldOrders.forEach {
                println(it)
            }
            /*orderList.forEachIndexed { index, plantEntities ->
                println("Orden No. $index -----")
                println(plantEntities)
                plantEntities.forEach {
                    println("Id: ${it.id}, Nombre: ${it.name}, Cantidad: ${it.quantity}")
                }
            }*/
            menuShow()
        }
    }

    fun checkOut() {
        val total = shoppingCart.sumOf { it.quantity }
        println("Su pedido actualmente tiene $total items")
        println("¿Desea finalizar su pedido?")
        println("1. Finalizar Pedido")
        println("2. Volver al menú Principal")
        println("Ingresa una opción")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> painCart()
            2 -> showMenu()
            else -> println("Opción inválida. Inténtalo de nuevo.")
        }
    }

    private fun painCart() {
        println("Su listado de plantas a comprar es el siguiente: ")
        println("------------------------------------------------------------")
        shoppingCart.forEach {
            println("Id: ${it.id}, Nombre: ${it.name}, Cantidad: ${it.quantity}, Precio: ${it.price}")
        }
        println("------------------------------------------------------------")
        println("Total a pagar: $${calcTotal()}")
        print("Presiona enter para continuar -> ")
        oldOrders.add(shoppingCart)
        shoppingCart.clear()
        readlnOrNull()?.toIntOrNull()
    }

    private fun calcTotal(): Double {
        var result = 0.0
        for (product in shoppingCart){
            result += product.totalPrice()
        }
        return result
    }

}