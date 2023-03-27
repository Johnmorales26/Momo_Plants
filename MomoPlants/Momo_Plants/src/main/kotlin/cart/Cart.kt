package cart

import dataAccess.PlantsDatabase
import entities.ItemEntity
import entities.OrderEntity
import utils.OrderStatus

class Cart {

    private val shoppingCart = OrderEntity()
    private val orderList = mutableListOf<OrderEntity>()
    private val plants = PlantsDatabase.getAllPlants()

    fun showMenu() {
        val menu = listOf(
            Pair(1,"Agregar planta al carrito"),
            Pair(2,"Buscar planta"),
            Pair(3,"Eliminar planta"),
            Pair(4,"Mostrar plantas del carrito"),
            Pair(5,"Mostrar plantas del catálogo"),
            Pair(6,"Finalizar Pedido"),
            Pair(7,"Ver pedidos anteriores"),
            Pair(8,"Salir")
        )
        return menu.forEach { option-> println("${option.first}.- ${option.second}") }
    }

    fun askPlantToAdd() {
        print("Ingresa el id de la planta: ")
        val id: Int? = readlnOrNull()?.trim()?.toIntOrNull()
        if (id != null && id in plants.indices) {
            print("Cantidad de plantas que deseas agregar: ")
            val quantity = readlnOrNull()?.toIntOrNull()
            if (quantity != null && quantity > 0) {
                val plant = plants[id]
                // TODO
                addItem(ItemEntity(plant, quantity))
                println("Se agregaron $quantity planta(s) de ${plant.name} al carrito.")
            } else {
                println("Error: cantidad invalida.")
            }
        } else {
            println("Error: Datos invalidos")
        }
    }

    private fun addItem(item: ItemEntity) = shoppingCart.items.add(item)

    fun askPlantToFind() {
        print("Ingresa el nombre de la planta a buscar: ")
        val nameP = readlnOrNull()?.trim()
        if (nameP != null) {
            val item = findItem(nameP)

            if (item != null) {
                println("La planta ${item.plant.name} se encuentra en el carrito.")
            } else {
                println("La planta $nameP no se encuentra en el carrito.")
            }
        } else {
            println("Error: datos ingresados inválidos.")
        }
    }

    private fun findItem(plantName: String): ItemEntity? = shoppingCart.items.find { item -> item.plant.name.lowercase().contains(plantName.lowercase()) }

    fun askPlantToRemove() {
        print("Ingresa el id de planta que deseas eliminar: ")
        val indexP = readlnOrNull()?.trim()?.toIntOrNull()
        if (indexP != null && indexP in 0 until shoppingCart.items.size){
            val itemToRemove = shoppingCart.items[indexP]
            println("¿Seguro que desea eliminar la planta ${itemToRemove.plant.name} del carrito ?")
            println("1.- Sí, eliminar")
            println("2.- Cancelar")
            when(readlnOrNull()?.trim()?.toIntOrNull()){
                1 -> {
                    removeItem(indexP)
                    println("${itemToRemove.plant.name} se ha eliminado del carrito")
                }
                else -> println("Presiona enter para continuar")
            }
        }else{
            println("No existe el id")
        }
    }

    private fun removeItem(id: Int) = shoppingCart.items.removeAt(id)

    fun showPlantsCart() {
        if (shoppingCart.items.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("Plantas en el carrito:")
            shoppingCart.items.forEachIndexed {
                    index, elemento -> println("Id: ${elemento.plant.id}, Nombre: ${elemento.plant.name}, Cantidad: ${elemento.quantity}")
            }
            println("Total ${shoppingCart.items.size}")
            menuShow()
        }
    }

    private fun menuShow(){
        var opcion: Int
        do {
            println()
            println("1. Edita una planta del carro")
            println("2. Eliminar una planta del carro")
            //println("3. Aplicar cupón")
            println("3. Salir\n")
            print("Selecciona una opción: ")
            println ()
            opcion = readln().toInt()
            when (opcion) {
                1 -> updatePlant()
                2 -> askPlantToRemove()
                //3 -> println("3!")
                3 -> println("Has regresado al menu principal")
                else -> println("Opción inválida. Inténtalo de nuevo.")
            }
        } while (opcion != 3)
    }

    private fun updatePlant(){
        print("Ingresa el id de la planta a cambiar cantidad: ")
        val indexP = readlnOrNull()?.trim()?.toIntOrNull()
        if (indexP != null && indexP in 0 until shoppingCart.items.size){
            print("Cantidad nueva de plantas que deseas agregar: ")
            val quantity = readlnOrNull()?.toIntOrNull()
            if (quantity != null && quantity > 0) {
                val plant = plants[indexP]
                println(plant)
                addItem(ItemEntity(plant, quantity))
                println("Se agregaron $quantity planta(s) de ${plant.name} al carrito.")
            } else {
                println("Error: cantidad invalida.")
            }
                removeItem(indexP)
        }else{
            println("No existe el id")
        }
    }

    fun showOldOrders(){
        if (orderList.isEmpty()) {
            println("No hay ordenes antiguas.")
        } else {
            println("Plantas en el carrito:")
            orderList.forEach {
                it.items.forEachIndexed { index, item ->
                    println("Id: ${index}, Nombre: ${item.plant.name}, Cantidad: ${item.quantity}")
                }
            }
            menuShow()
        }
    }

    fun checkOut(){
        val total = shoppingCart.items.sumOf { it.quantity }
        println("Su pedido actualmente tiene $total items")
        println("¿Desea finalizar su pedido?")
        println("1. Finalizar Pedido")
        println("2. Cancelar Pedido")
        println("2. Volver al menú Principal")
        println("Ingresa una opción")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> updatedStatus(OrderStatus.PAID)
            2 -> updatedStatus(OrderStatus.CANCELED)
            3 -> showMenu()
            else -> println("Opción inválida. Inténtalo de nuevo.")
        }
    }

    private fun updatedStatus(statusUpdate: OrderStatus) {
        shoppingCart.apply { status = statusUpdate }
        menuShow()
    }

}