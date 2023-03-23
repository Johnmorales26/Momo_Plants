package cart

import Plant
//import java.util.UUID
//data class Plant(val name: String, val id:String)

class Cart {
    private val shoppingCart = mutableListOf<Item>()
    private val plants = Catalogue.plants

    fun showMenu() {
        val menu = listOf(
            Pair(1,"Agregar planta al carrito"),
            Pair(2,"Buscar planta"),
            Pair(3,"Eliminar planta"),
            Pair(4,"Mostrar plantas"),
            Pair(5,"Salir")
        )
        return menu.forEach { option-> println("${option.first}.- ${option.second}") }
    }
    fun askPlantToAdd() {
        print("Ingresa el id de la planta: ")
        val id: Int = readlnOrNull()?.trim() as Int

        if (id != null) {
            print("Cantidad de plantas que deseas agregar: ")
            val quantity = readLine()?.toIntOrNull()
            if (quantity != null && quantity > 0) {
                val plant = plants[id]
                addItem(plant, quantity)
                /*
                for (i in 1..quantity){
                    val plant = Plant(nameP,UUID.randomUUID().toString())
                }*/
                println("Se agregaron $quantity planta(s) de ${plant.name} al carrito.")
            } else {
                println("Error: cantidad invalida.")
            }
        } else {
            println("Error: Datos invalidos")
        }
    }

    private fun addItem(plant: Plant, quantity: Int) {
        shoppingCart.add(Item(plant, quantity))
    }
    fun askPlantToFind() {
        print("Ingresa el nombre de la planta a buscar: ")
        val nameP = readLine()?.trim()

        if (nameP != null) {
            val item = findItem(nameP)

            if (item != null) {
                println("La planta $nameP se encuentra en el carrito.")
            } else {
                println("La planta $nameP no se encuentra en el carrito.")
            }
        } else {
            println("Error: datos ingresados inválidos.")
        }
    }

    private fun findItem(plantName: String): Item? {
        val item = shoppingCart.find { item -> item.plant.name.lowercase().contains(plantName.lowercase()) }

        return item
    }
    fun askPlantToRemove() {
        print("Ingresa el id de planta que deseas eliminar: ")
        val indexP = readLine()?.trim()?.toIntOrNull()

        if (indexP != null && indexP < shoppingCart.size){
            val itemToRemove = shoppingCart[indexP]

            println("¿Seguro que desea eliminar la planta ${itemToRemove.plant.name} del carrito ?")
            println("1.- Sí, eliminar")
            println("2.- Cancelar")
            val resp = readLine()?.trim()?.toIntOrNull()
            if (resp == 1){
                removeItem(indexP)
                println("$indexP se ha eliminado del carrito ${itemToRemove.plant.name}")
            }else{
                println("Presiona enter para continuar")
            }

        }else{
            println("no existe el id")
        }
    }

    private fun removeItem(id: Int) {
        shoppingCart.removeAt(id)
    }
    fun showPlants() {
        if (shoppingCart.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("Plantas en el carrito:")
            shoppingCart.forEachIndexed {
                    index, elemento -> { println("Id: ${index}, Nombre: ${elemento.plant.name}, Cantidad: ${elemento.quantity}")}
            }
            println("Total ${shoppingCart.size}")
        }
    }
}